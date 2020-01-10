package com;

import com.lsb.CheckLSB;
import com.lsb.LSB;
import com.utils.CommonUtils;
import java.io.File;



public class Watermarking {
  private static final String KEY_W = "-w";
  private static final String KEY_C = "-c";

  private String key;
  private String msg;
  private String password;
  private File inputFile;
  private File outputFile;

  /**
   * Start program from here
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Java Audio Water Marking Utility v." + LSB.VERSION);
//    if (checkInput(args)) {
//
//    }
    new Watermarking(args).start();
//    System.out.println("Hello");
  }

  /**
   * Init parameters
   *
   * @param args
   */
  public Watermarking(String[] args) {
    this.key = "-c";
    this.inputFile = new File("src/main/java/com/BrickHouse.wav-watermark.wav");
    // Create output file name
    this.outputFile = CommonUtils.newFileName(inputFile, "-watermark");

    this.msg = "I love you";
    this.password = "123456789abc";
  }

  /**
   * Start water marking routine depending from flags
   */
  public void start() {
    LSB lsb = new LSB();
    switch (key) {
      case KEY_W:
        boolean result = lsb.embedMessage(inputFile, outputFile, msg, -1, password);
        System.out.println("Watermarking complete!");
        break;

      case KEY_C:
        CheckLSB checkLSB = new CheckLSB();
        result = checkLSB.check(inputFile);
        if (!result) {
          showResult(result, "null");
          return;
        }
        String msg = lsb.retrieveMessage(checkLSB, password);
        showResult(this.msg.equals(msg), msg);
        break;
    }
  }

  private void showResult(boolean result, String message) {
    System.out.println(String.format("Does %s contains watermark: %b, message retrieved: %s", inputFile.getName(), result, message));
  }

  private static boolean checkInput(String[] args) {
    if (args.length < 4) {
      showUsage();
      return false;
    } else if (!KEY_W.equals(args[0]) && !KEY_C.equals(args[0])) {
      showUsage();
      return false;
    }
    return true;
  }

  private static void showUsage() {
    System.out.println("Usage: java -jar watermark.jar [-w] [-c] input_file water_mark_msg password \n");
    System.out.println("Options:");
    System.out.println("\t -w\t\t\t Watermarking (input_file).");
    System.out.println("\t -c\t\t\t Check if (input_file) was watermarked.");
  }
}
