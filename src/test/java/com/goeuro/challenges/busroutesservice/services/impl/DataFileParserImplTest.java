package com.goeuro.challenges.busroutesservice.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataFileParserImplTest {

  private static final String VALID_DATA_FILE = "src/test/resources/data";
  private static final String INVALID_FILE_1 = "src/test/resources/not-existing-file";
  private static final String INVALID_FILE_2 = "src/test/resources/missed-number-of-routes";
  private static final String INVALID_FILE_3 = "src/test/resources/number-of-routes-mismatch";
  private static final String INVALID_FILE_4 = "src/test/resources/empty-file";
  private static final String INVALID_FILE_5 = "src/test/resources/invalid-numbers";

  @Rule
  public ExpectedException expected = ExpectedException.none();

  private DataFileParserImpl unit = new DataFileParserImpl();

  @Test
  public void testProcessDataFile() {
    final Map<Integer, Set<Integer>> expected =
        ImmutableMap.<Integer, Set<Integer>>builder()
            .put(0, ImmutableSet.of(0, 2))
            .put(1, ImmutableSet.of(0, 1))
            .put(2, ImmutableSet.of(0))
            .put(3, ImmutableSet.of(0, 1))
            .put(4, ImmutableSet.of(0, 2))
            .put(5, ImmutableSet.of(1))
            .put(6, ImmutableSet.of(1, 2))
            .build();

    final Map<Integer, Set<Integer>> result = unit.processDataFile(VALID_DATA_FILE);
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testProcessDataFileFileDoesNotExist() {
    expected.expectMessage("Error occurred during file processing");
    unit.processDataFile(INVALID_FILE_1);
  }

  @Test
  public void testProcessDataFileWithMissedNumberOfRoutes() {
    expected.expectMessage("Invalid data file: "
        + "first line does not contain number of routes");
    unit.processDataFile(INVALID_FILE_2);
  }

  @Test
  public void testProcessDataFileNumberOfRoutesMismatch() {
    expected.expectMessage("Invalid data file: "
        + "number of routes (3) does not march with number of lines (2)"
    );
    unit.processDataFile(INVALID_FILE_3);
  }

  @Test
  public void testProcessDataFileWithEmptyFile() {
    expected.expectMessage("Invalid data file: data file is empty");
    unit.processDataFile(INVALID_FILE_4);
  }

  @Test
  public void testProcessDataFileWithNotOnlyNumbers() {
    expected.expectMessage("Error occurred during file processing");
    unit.processDataFile(INVALID_FILE_5);
  }


}