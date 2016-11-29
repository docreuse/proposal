public class UnsignedInteger {

  /**
  * returns the addition of the two parameters
  *
  * @param a first integer
  * @param b second integer
  * @return the addition of the parameters
  * @throws Exception the parameters have to be positive integers
  */

  public int add(int a, int b) throws Exception {

    if ((a < 0) || (b < 0)) 
      throw new Exception();

    return a + b;
  }


  /**
  * returns the addition of the two parameters
  *
  * @param a {@reuse UnsignedInteger#add(int, int):a}
  * @param b {@reuse UnsignedInteger#add(int, int):b}
  * @param c third integer
  * @return {@reuse UnsignedInteger#add(int, int)}
  * @throws Exception {@reuse UnsignedInteger#add(int, int)}
  */

  public int add(int a, int b, int c) throws Exception {
    return add(add(a,b), c);
  }

}