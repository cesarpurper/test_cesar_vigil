package Util

import scala.annotation.tailrec

object WordUtils {


  /**
   *
   * This function gets any given string and break it in lines of 'carLimit' length. No words are broken in the middle in the proccess.
   *
   *
   * @param anyText The text that will be broken in 'charLimit' chars
   * @param charLimit The max amount of chars in each line
   * @return An Option containing either the broken text or None
   */
  def breakStringInXChar(anyText: String, charLimit: Int): Option[String] = {

    /**
     * This loop function is the function that will do the recursion and split strings with line separators. The char limit of each line is set by the 'charLimit' variable
     * passed as argument in the outer function.
     *
     * To solve possible memory issues it was done using tail recursion.
     *
     *
     * @param returnString the accumulator string with the words that are being broken and joined by line separators
     * @param tailString the remainder of the string that are being split by the recursion
     * @return A String with the text broken in lines
     */
    @tailrec
    def loop(returnString: String, tailString: String): String = {

      //if the size of the tailString parameter is smaller than the 'charLimit' it returns the result of the line breaking as String.
      if (tailString.length <= charLimit) {
        returnString + tailString
      }else {
        //if the tailString size is greater than the 'charLimit' it means we have to break the string

        //first we substring the passed string in the Xth character.
        val firstPart = tailString.substring(0, charLimit + 1)
        //we are using the +1 in the substring to handle cases where the Xth char is the last char of a word.
        //In the next steps we will use the index of the last space char as reference to break the string in the correct place.
        //With the +1, strings that would eventually end in the word's last character, now will end in a space character
        //and the last space char index logic covers this case


        //since one of the requisites is to not break any word in the middle, we have to get the last ' '(space) char
        //index in this 'firstPart' string.
        //this index will be used further to do the final substring in the text.
        val lastSpaceCharIndex = firstPart.lastIndexOf(" ")

        //this is the final broken line, within X chars and respecting words boundaries
        val brokenLine = firstPart.substring(0, lastSpaceCharIndex)
        loop(returnString = returnString + brokenLine + System.lineSeparator(), tailString = tailString.substring(lastSpaceCharIndex + 1))
      }
    }

    //now we start the loop passing as parameter a empty string and the text to be broken
    //since there are cases that the passed arguments would make the line breaking impossible and a exception will be rose
    //I am treating exceptions like this
    try{
      //if everything works fine a Option[String] will be returned containing the string with line separators as result.
      Some(loop("", anyText))

    } catch {
      //If any exception is caught 'None' is returned as result of the algorithm being unable to break the string
      case e : Exception => None

    }


  }

}
