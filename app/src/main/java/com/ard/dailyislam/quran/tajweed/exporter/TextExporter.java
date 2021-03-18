package com.ard.dailyislam.quran.tajweed.exporter;

import com.ard.dailyislam.quran.tajweed.model.Result;

import java.util.List;

public class TextExporter implements Exporter {


  @Override
  public String export(String ayahText, List<Result> results) {
    System.out.println("Considering: " + ayahText);
    for (Result result : results) {
      System.out.println("matched " + result.resultType.debugName +
          " at " + result.start + " to " + result.ending);
    }

    return  "Tes";

  }
}
