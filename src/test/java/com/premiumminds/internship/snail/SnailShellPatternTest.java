package com.premiumminds.internship.snail;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by aamado on 05-05-2023.
 */
@RunWith(JUnit4.class)
public class SnailShellPatternTest {

  SnailMatrixTestGenerator generator = new SnailMatrixTestGenerator();

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public SnailShellPatternTest() {
  };

  @Test
  public void ScreenLockinPatternTestFirst3Length2Test()
      throws InterruptedException, ExecutionException, TimeoutException {
    int[][] matrix = { { 1, 2, 3 }, { 8, 9, 4 }, { 7, 6, 5 } };
    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    assertArrayEquals(result, expected);
  }

  @Test
  public void ScreenLockinPatternTestSecond1LengthTest()
          throws InterruptedException, ExecutionException, TimeoutException {
    // test length 1
    int[][] matrix = { { 1 } };
    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int[] expected = { 1 };
    assertArrayEquals(result, expected);
  }
  @Test
  public void ScreenLockinPatternTestThird0lengthTest()
          throws InterruptedException, ExecutionException, TimeoutException {
    // test length 0
    int[][] matrix = { {  } };
    Future<int[]>count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int[] expected = new int[0];
    assertArrayEquals(result, expected);
  }
  public void FixedSizeSortedMatrixTest(int n)
          throws InterruptedException, ExecutionException, TimeoutException {
    // test length 0
    int[][] matrix = generator.generateTest(n);
    Future<int[]> count = new SnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int index = 0;
    // test if list is ordered
    while(index < result.length -1){
      assertTrue(result[index] < result[index+1]);
      index++;
    }
    // test if every element is in the result list
    assertTrue(result.length == n * n);
  }

  @Test
  public void SortedMatrixTest()
          throws InterruptedException, ExecutionException, TimeoutException {
    // size of matrix to test (matrix e*e)
    for(int i = 0; i < 50;i++){
    for(int e: new int[]{0,1,2,3,4,5,6,7,8,9,10}){
      FixedSizeSortedMatrixTest(e*100); // test with bigger matrixes

    }}
  }

  public void ParallelFixedSizeSortedMatrixTest(int n)
          throws InterruptedException, ExecutionException, TimeoutException {
    // test length 0
    int[][] matrix = generator.generateTest(n);
    Future<int[]> count = new ParallelSnailShellPattern().getSnailShell(matrix);
    int[] result = count.get(10, TimeUnit.SECONDS);
    int index = 0;
    // test if list is ordered
    while(index < result.length -1){
      assertTrue(result[index] < result[index+1]);
      index++;
    }
    // test if every element is in the result list
    assertTrue(result.length == n * n);
  }
  @Test
  public void SortedParallelMatrixTest()
          throws InterruptedException, ExecutionException, TimeoutException {
    // size of matrix to test (matrix e*e)
    for(int i = 0; i < 50;i++){
      for(int e: new int[]{0,1,2,3}){
        ParallelFixedSizeSortedMatrixTest(e*100); // test with bigger matrixes
      }}
  }
}