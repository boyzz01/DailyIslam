package com.ard.quran.tajweed.exporter;

import com.ard.quran.tajweed.model.Result;
import com.ard.quran.tajweed.model.ResultType;
import com.ard.quran.tajweed.model.ResultUtil;
import com.ard.quran.tajweed.model.TwoPartResult;

import java.util.List;

/**
 * HtmlExporter
 * Exports the highlighted tajweed results for the given ayahs as html.
 * Note that this only works in Firefox due to a bug in Webkit - see:
 * https://bugs.webkit.org/show_bug.cgi?id=6148 and
 * http://stackoverflow.com/questions/11155849/partially-colored-arabic-word-in-html
 * for more details.
 */
public class HtmlExporter implements Exporter {

  @Override
  public void onOutputStarted() {
    String builder = "<html>" + "<head>" +
        "<meta charset=\"UTF-8\">" +
        "<style>" +
        "body { font-size: 350%; }" +
        "</style>" +
        "<title>Tajweed Test</title>" +
        "</head>";
    write(builder);
  }

  @Override
  public void onOutputCompleted() {
    write("</html>");
  }

  @Override
  public String export(String ayah, List<Result> results) {
    // rules are sorted, but we need to remove or update overlapping rules
    // because this exporter currently doesn't support backtracking.
    ResultUtil.INSTANCE.fixOverlappingRules(results);

    StringBuilder buffer = new StringBuilder("");
    int currentIndex = 0;
    for (Result result : results) {
      int start = result.getMinimumStartingPosition();
      if (start > currentIndex) {
        buffer.append(ayah.substring(currentIndex, start));
      }

      start = result.getMinimumStartingPosition();
      if (result instanceof TwoPartResult) {
        TwoPartResult twoPartResult = (TwoPartResult) result;
        if (start == twoPartResult.start) {
          // first, then second
          appendRule(buffer, ayah, twoPartResult.resultType,
              twoPartResult.start, twoPartResult.ending);
          if (twoPartResult.secondStart - twoPartResult.ending > 0) {
            buffer.append(ayah.substring(twoPartResult.ending, twoPartResult.secondStart));
          }
          appendRule(buffer, ayah, twoPartResult.secondResultType,
              twoPartResult.secondStart, twoPartResult.secondEnding);
        } else {
          // second, then first
          appendRule(buffer, ayah, twoPartResult.secondResultType,
              twoPartResult.secondStart, twoPartResult.secondEnding);
          if (twoPartResult.start - twoPartResult.secondEnding > 0) {
            buffer.append(ayah.substring(twoPartResult.secondEnding, twoPartResult.start));
          }
          appendRule(buffer, ayah,
              twoPartResult.resultType, twoPartResult.start, twoPartResult.ending);
        }
      } else {
        appendRule(buffer, ayah, result.resultType, result.start, result.ending);
      }
      currentIndex = result.getMaximumEndingPosition();
    }

    buffer.append(ayah.substring(currentIndex));
    write(buffer.toString());

    String a = buffer.toString();
    return  a;
  }

  private void appendRule(StringBuilder buffer, String ayah, ResultType type, int start, int end) {
    buffer.append("<font color=\"#");
    buffer.append(getColorForRule(type));
    buffer.append("\">");
    buffer.append(ayah.substring(start, end));
    buffer.append("</font>");
  }

  private String getColorForRule(ResultType type) {
    return type.color;
  }

  private void write(String string) {
    System.out.println(string);
  }
}
