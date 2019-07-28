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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * This class parses input data file and stores it to Map where key is a route id and value is a
 * List of station ids in the same order as in a file.
 */
@Component
public class DataFileParserImpl implements DataFileParser {

  private static final Logger LOG = LoggerFactory.getLogger(DataFileParserImpl.class);

  private static final int MIN_NUM_OF_VALUES_PER_LINE = 3;

  private static final String VALUE_SEPARATOR = "\\s";

  /**
   * Reads file and store parsed data to Map.
   *
   * @param path a string containing data file path
   * @return a Map where key is a routeId and value is a List of stationIds
   * @throws DataFileException if data file is invalid
   */
  @Override
  public Map<Integer, List<Integer>> processDataFile(String path) {
    LOG.debug("Started processing data file: {}", path);
    final List<List<Integer>> parsedLines = openAndParseFile(path);

    validateParsedData(parsedLines);

    final Map<Integer, List<Integer>> routesMap = new HashMap<>();

    final Iterator<List<Integer>> linesIterator = parsedLines.listIterator(1);
    while (linesIterator.hasNext()) {
      storeStationsPerRoute(routesMap, linesIterator.next());
    }
    final Map<Integer, List<Integer>> result = Collections.unmodifiableMap(routesMap);
    LOG.debug("Finished processing data file");
    return result;
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

  private void storeStationsPerRoute(Map<Integer, List<Integer>> routes, List<Integer> values) {
    validateLineData(values);
    routes.put(values.get(0), Collections.unmodifiableList(values.subList(1, values.size())));
  }

  private void validateLineData(List<Integer> values) {
    if (CollectionUtils.isEmpty(values) ||
        values.size() < MIN_NUM_OF_VALUES_PER_LINE) {
      final String message = String.format("Invalid data file: line contains less than %s value",
          MIN_NUM_OF_VALUES_PER_LINE);

      LOG.error(message);
      throw new DataFileException(message);
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
