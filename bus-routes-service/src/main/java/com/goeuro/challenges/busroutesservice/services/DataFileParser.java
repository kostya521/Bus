package com.goeuro.challenges.busroutesservice.services;

import java.util.Map;
import java.util.Set;

public interface DataFileParser {

  Map<Integer, Set<Integer>> processDataFile(String path);

}
