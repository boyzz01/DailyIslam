package com.ard.dailyislam.quran.tajweed.exporter;

import com.ard.dailyislam.quran.tajweed.model.Result;

import java.util.List;

public interface Exporter {
  String export(String ayah, List<Result> results);

  default void onOutputStarted() {}
  default void onOutputCompleted() {}
}
