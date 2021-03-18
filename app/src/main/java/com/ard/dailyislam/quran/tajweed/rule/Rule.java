package com.ard.dailyislam.quran.tajweed.rule;



import com.ard.dailyislam.quran.tajweed.model.Result;

import java.util.List;

public interface Rule {
  List<Result> checkAyah(String ayah);
}
