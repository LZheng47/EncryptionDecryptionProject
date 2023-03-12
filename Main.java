class Main {
  public static void main(String[] args) {
    String msg = Input.readFile("original.txt");
    
    // Encode message
    String encodedMsg = encode(msg);
    Input.writeFile("encodedMsg.txt", encodedMsg);

    // Decode message
    String decodedMsg = decode(encodedMsg);
    Input.writeFile("decodedMsg.txt", decodedMsg);

  }


  public static String encode(String msg){
    String level1 = cipherDivBy5(msg);
    String level2 = cipher1To9(level1);
    String level3 = everyNthChar(level2);
    String level4 = ascii2DigitSwitch(level3);
    String level5 = addRandChar(level4);
    String level6 = reverse(level5);

    // Displays encoded message for every level
    System.out.println("=================== Encoding Process ===================\n");
    System.out.println("Original:\n" + msg + "\n");
    System.out.println("Level 1:\n" + level1 + "\n");
    System.out.println("Level 2:\n" + level2 + "\n");
    System.out.println("Level 3:\n" + level3 + "\n");
    System.out.println("Level 4:\n" + level4 + "\n");
    System.out.println("Level 5:\n" + level5 + "\n");
    System.out.println("Level 6:\n" + level6 + "\n");

    // Returns final encoded message
    return level6;
  }


  public static String decode(String msg){
    String level6 = reverse(msg);
    String level5 = removeRandChar(level6);
    String level4 = reverseAscii2DigitSwitch(level5);
    String level3 = reverseNthChar(level4);
    String level2 = cipher1To9(level3);
    String level1 = reverseCipherDivBy5(level2);

    // Displays decoded message for every level
    System.out.println("\n=================== Decoding Process ===================\n");
    System.out.println("Level 6:\n" + msg + "\n");
    System.out.println("Level 5:\n" + level6 + "\n");
    System.out.println("Level 4:\n" + level5 + "\n");
    System.out.println("Level 3:\n" + level4 + "\n");
    System.out.println("Level 2:\n" + level3 + "\n");
    System.out.println("Level 1:\n" + level2 + "\n");
    System.out.println("Original:\n" + level1 + "\n");
    System.out.println("===================");

    // Returns decoded message
    return level1;
  }


  public static String cipherDivBy5(String msg){
    String build = "";
    char newChar;
    int changeCount;
    int ascii;

    for(int i = 0; i <= msg.length() - 1; i++){
      changeCount = 0;
      ascii = (int)msg.charAt(i);

      if(ascii % 5 == 0){
        ascii *= 2;
      }else{
        while(true){
          ascii += 3; // add 3 to current ascii
          changeCount++;
          if(ascii % 5 == 0){
            break;
          }

          ascii -= 1; // subtract 1 from current ascii
          changeCount++;
          if(ascii % 5 == 0){
            break;
          }
        }
      }
      
      build += (char)ascii;
      build += (char)(100 + changeCount);
    }

    return build;
  }


  public static String cipher1To9(String msg){
    String build = "";
    int count = 1;
    int ascii;

    for(int i = 0; i <= msg.length() - 1; i++){
      ascii = (int)msg.charAt(i);

      if(ascii % 2 == 0){  // if even, shift right
        build += (char)(ascii + count);
      }else{  // if odd, shift left
        build += (char)(ascii - count);
      }

      count += 2;
      if(count == 11){ // Reset count to 1
        count = 1;
      }
      
    }

    return build;
  }


  public static String everyNthChar(String msg){
    String build = "";
    String strSection = "";

    for(int n = 0; n <= 2; n++){

      for(int i = 0; i <= msg.length() - 1; i += 3){ // split str into sections with 3 char each
        
        if(msg.length() - i < 3){ // tests if it is final section of 3
          strSection = msg.substring(i); // overload
        }else{
          strSection = msg.substring(i, i + 3);
        }

        if (!(strSection.length() < n + 1)){
          build += strSection.charAt(n);
        }
        // Every nth position of the strSection (substring)
      }

    }
    
    return build;
  }


  public static String ascii2DigitSwitch(String msg){
    String build = "";
    int charAscii;
    String asciiToStr;
    String newAscii;

    for(int i = 0; i <= msg.length() - 1; i++){
      charAscii = (int)msg.charAt(i);
      asciiToStr = "" + charAscii;
      newAscii = "" + (int)(Math.random()*9 + 1);
      
      for(int letterIdx = 0; letterIdx <= asciiToStr.length() - 2; letterIdx += 2){
        newAscii += asciiToStr.charAt(letterIdx + 1);
        newAscii += asciiToStr.charAt(letterIdx);
      }

      if(asciiToStr.length() % 2 == 1){ // If odd number of digits
        newAscii += asciiToStr.charAt(asciiToStr.length()-1);
      }

      build += (char)Integer.parseInt(newAscii);  
    }
    
    return build;
  }


  public static String addRandChar(String msg){
    String build = "";
    
    for(int i = 0; i <= msg.length() - 1; i++){
      if(i % 3 == 0){
        build += (char)((int)(Math.random() * 32000));
      }
      build += msg.charAt(i);
    }

    return build;
  }


  public static String reverse(String msg){
    String build = "";
    
    for(int i = msg.length() - 1; i >= 0; i--){
      build += msg.charAt(i);
    }

    return build;
  }


  public static String reverseCipherDivBy5(String msg){
    String build = "";
    int changeCount;
    int ascii;

    for(int i = 0; i <= msg.length() - 2; i+=2){
      ascii = (int)msg.charAt(i);
      changeCount = (int)msg.charAt(i + 1) - 100;

      if(changeCount == 0){
        ascii *= 0.5;
      }else{
        for(int x = changeCount; x > 0; x--){
          if(x % 2 == 0){
            ascii += 1;
          }else{
            ascii -= 3;
          }
        }
      }

      build += (char)ascii;
    }

    return build;
  }

  
  public static String reverseNthChar(String msg){
    String build = "";
    int maxCharPerGroup = (int)Math.ceil(msg.length() / 3.0);
    int groupsWMaxChar = msg.length() % 3;
    int groupNumber = 0;
    String groupStr = "";
    
    int groupStartIdx = 0;
    int groupEndIdx = 0;

    int charPos = -1;

    for(int i = 0; i <= msg.length() - 1; i++){
      groupNumber++; 
      if(groupNumber == 4){
        groupNumber = 1;
      }

      if(i % 3 == 0){
        charPos++;
      }
      

      if(groupNumber <= groupsWMaxChar || groupsWMaxChar == 0){
        groupStartIdx = (groupNumber - 1) * maxCharPerGroup;
        groupEndIdx = groupNumber * maxCharPerGroup;
      }else{
        groupStartIdx = groupEndIdx;
        groupEndIdx += maxCharPerGroup - 1;
      }
      
      groupStr = msg.substring(groupStartIdx, groupEndIdx);
      
      build += groupStr.charAt(charPos);
    }

    return build;
  }

  public static String reverseAscii2DigitSwitch(String msg){
    String build = "";
    int charAscii;
    String asciiToStr;
    String newAscii;

    for(int i = 0; i <= msg.length() - 1; i++){
      charAscii = (int)msg.charAt(i);
      asciiToStr = "" + charAscii;
      newAscii = "";

      for(int letterIdx = 1; letterIdx <= asciiToStr.length() - 2; letterIdx += 2){
        newAscii += asciiToStr.charAt(letterIdx + 1);
        newAscii += asciiToStr.charAt(letterIdx);
      }

      if(asciiToStr.length() % 2 == 0){ // If even number of digits
        newAscii += asciiToStr.charAt(asciiToStr.length()-1);
      }

      build += (char)Integer.parseInt(newAscii);  
    }
    
    return build;
  }


  public static String removeRandChar(String msg){
    String build = "";
    for(int i = 0; i <= msg.length() - 1; i++){
      if(i % 4 != 0){
        build += msg.charAt(i);
      }
    }
    return build;
  }
}