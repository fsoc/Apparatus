
class Creator {
  public static void main(String args[])
  {
    System.out.println("1000 1000");
    for (int i = 0; i < 1000; i++) {
      String number="";
      for (int j = 0; j < 1000; j++) {
        if (i<=j) {
          number +="1";
        } else {
          number +="0";
        }
      }
      System.out.println(number+ "\n" +number);
    }
  }
}
