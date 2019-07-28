package com.goeuro.challenges.busroutesservice.services.impl;

import com.goeuro.challenges.busroutesservice.exceptions.DataFileException;
import com.goeuro.challenges.busroutesservice.services.DataFileParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataFileParserImpl implements DataFileParser {

  private static final Logger LOG = LoggerFactory.getLogger(DataFileParserImpl.class);

  private static final String VALUE_SEPARATOR = "\\s";


  @Override
  public Map<Integer, Set<Integer>> processDataFile(String path) {
    LOG.debug("Started processing data file: {}", path);
    final HashMap<Integer, Set<Integer>> stationToRouteMap = new HashMap<>();
    final List<List<Integer>> parsedLines = openAndParseFile(path);

    validateParsedData(parsedLines);

    final Iterator<List<Integer>> linesIterator = parsedLines.listIterator(1);
    while (linesIterator.hasNext()) {
      final List<Integer> parsedLine = linesIterator.next();
      mapStationToRoute(stationToRouteMap, parsedLine);
    }

    final Map<Integer, Set<Integer>> result = makeDataUnmodifiable(stationToRouteMap);
    LOG.debug("Finished processing data file");
    return result;
  }

  private Map<Integer, Set<Integer>> makeDataUnmodifiable(HashMap<Integer, Set<Integer>> map) {
    map.keySet().forEach(key ->
        map.put(key, Collections.unmodifiableSet(map.get(key)))
    );
    return Collections.unmodifiableMap(map);
  }

  private List<List<Integer>> openAndParseFile(String path) {
    final Path filePath = Paths.get(path);

    try {
      return Files.lines(filePath)
          .filter(StringUtils::isNotBlank)
          .map(this::parseLineToIntegers)
          .collect(Collectors.toList());
    } catch (IOException e) {
      LOG.error("Error occurred during file processing", e);
      throw new DataFileException("Error occurred during file processing", e);
    }
  }

  private List<Integer> parseLineToIntegers(String line) {
    try {
      return Arrays.stream(line.split(VALUE_SEPARATOR))
          .map(Integer::valueOf)
          .collect(Collectors.toList());
    } catch (NumberFormatException e) {
      LOG.error("Error occurred during file processing", e);
      throw new DataFileException("Error occurred during file processing", e);
    }
  }

  private void mapStationToRoute(HashMap<Integer, Set<Integer>> stationToRouteMap,
      List<Integer> values) {
    final Iterator<Integer> iterator = values.iterator();
    final Integer routeId = iterator.next();

    while (iterator.hasNext()) {
      final Integer stationId = iterator.next();
      if (stationToRouteMap.containsKey(stationId)) {
        stationToRouteMap.get(stationId).add(routeId);
      } else {
        final Set<Integer> routes = new HashSet<>();
        routes.add(routeId);
        stationToRouteMap.put(stationId, routes);
      }
    }
  }

  private void validateParsedData(List<List<Integer>> lines) {
    if (CollectionUtils.isEmpty(lines)) {
      LOG.error("Invalid data file: data file is empty");
      throw new DataFileException("Invalid data file: data file is empty");
    }
    final List<Integer> firstLine = lines.get(0);
    if (firstLine.size() != 1) {
      LOG.error("Invalid data file: first line does not contain number of routes");
      throw new DataFileException(
          "Invalid data file: first line does not contain number of routes");
    }
    final Integer numberOfRoutes = firstLine.get(0);
    if (numberOfRoutes != lines.size() - 1) {
      final String message = String.format("Invalid data file: "
              + "number of routes (%s) does not march with number of lines (%s)",
          numberOfRoutes, lines.size());
      LOG.error(message);
      throw new DataFileException(message);
    }
  }

}
