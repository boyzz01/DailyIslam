package com.ard.quran.tajweed.rule;



import com.ard.quran.tajweed.model.Result;

import java.util.List;

public interface Rule {
  List<Result> checkAyah(String ayah);
}
