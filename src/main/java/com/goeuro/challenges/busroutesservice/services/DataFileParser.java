package com.goeuro.challenges.busroutesservice.services;

import java.util.List;
import java.util.Map;

public interface DataFileParser {

  Map<Integer, List<Integer>> processDataFile(String path);

}
