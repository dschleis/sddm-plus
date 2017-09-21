/*
 * sddm-plus
 * @author  David Schleis dschleis@gmail.com
 * @version 0.1
 * @date 20-Sep-2017
 */

import javax.swing.JOptionPane
import javax.swing.JDialog
import javax.swing.JFrame

/**
 * Displays a message in a dialog box and returns the string that is input.
 *
 * @param question The message that is displayed in the dialog box
 * @return A String object containing the user input
 */
 String ask (String question = 'Please provide input') {
   JOptionPane.showInputDialog(question)
 }

/**
 * Displays a message in a dialog box.
 *
 * @param message The message that is displayed in the dialog box
 */
 Void show (String message) {
    JOptionPane.showMessageDialog(null,message)
 }

 /**
  * Writes to the Data Modeler log window.
  *
  * @param message The message that is displayed in the log window
  */
Void log(String message) {
    def app = oracle.dbtools.crest.swingui.ApplicationView;
    app.log(message)
}

oracle.dbtools.crest.model.design.relational.Table.metaClass {
  /**
   * Returns all columns of a table.
   *
   * @return List of Column objects.
   */
  getColumns = {->
    return delegate.getElements()
  }

  /**
   * Returns the column of a table with the given name (case insensitive).
   *
   * @param matcher A String that is the name of the column
   * @return A Column object.
   */
  getColumnWhereNameEquals = {String matcher  ->
    cols = delegate.getElements()
    for (col in cols) {
      if (col.name.toUpperCase() == matcher.toUpperCase())
        return col
    }
    return null
  }

  /**
   * Returns a List of Column objects that match the given matcher (case insensitive).
   *
   * This method compares the column names with the matcher and returns those column
   * objects that match.
   *
   * The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.
   * A simple string: 'test'
   * A string contining a list of comma-seperated values: 'abc, 123, *test'
   * A Groovy List of strings: ['abc', '%123', 'test*']
   * @param matcher A String or List
   * @return List of Column objects.
   */
  getColumnsWhereNameLike = { matcher  ->
    return getThings(delegate.getElements(), matcher)
  }

  /**
   * Returns a List of Column objects that do not match the given matcher (case insensitive).
   *
   * This method compares the column names with the matcher and returns those column
   * objects that do not match.
   *
   * The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.
   * A simple string: 'test'
   * A string contining a list of comma-seperated values: 'abc, 123, *test'
   * A Groovy List of strings: ['abc', '%123', 'test*']
   *
   * @param matcher A String or List
   * @return List of Column objects.
   */
  getColumnsWhereNameNotLike= { matcher  ->
    return getThings(delegate.getElements(), matcher, true)
  }
}

 /**
  * Returns a list of the table objects found in the current relational model.
  *
  * @return List of Table objects.
  */
def getTables() {
  model.tableSet
}

  /**
   * Returns the table with the given name (case insensitive) found in the current relational model.
   *
   * @param matcher A String that is the name of the table
   * @return A Table object.
   */
def getTableWhereNameEquals(String matcher) {
  model.tableSet.getByName(matcher)
}

  /**
   * Returns a List of Table objects from the current relational model that match the given matcher (case insensitive).
   *
   * This method compares the table names with the matcher and returns those table
   * objects that match.
   *
   * The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.
   * A simple string: 'test'
   * A string contining a list of comma-seperated values: 'abc, 123, *test'
   * A Groovy List of strings: ['abc', '%123', 'test*']
   *
   * @param matcher A String or List
   * @return List of Table objects.
   */
def getTablesWhereNameLike(matcher) {
  getThings(model.tableSet, matcher)
}

  /**
   * Returns a List of Table objects from the current relational model that do not match the given matcher (case insensitive).
   *
   * This method compares the table names with the matcher and returns those table
   * objects that do not match.
   *
   * The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.
   * A simple string: 'test'
   * A string contining a list of comma-seperated values: 'abc, 123, *test'
   * A Groovy List of strings: ['abc', '%123', 'test*']
   *
   * @param matcher A String or List
   * @return List of Table objects.
   */
def getTablesWhereNameNotLike(matcher) {
  getThings(model.tableSet, matcher, true)
}

/*!
 * Private method to return objects from a list based on whether they match
 * or do not match a given string or list of strings
 *
 * @param thingList A List of objecs
 * @param includeMe A String or List
 * @param exclude A flag indicating if the returned values should include or exclude includeMe
 * @return List of objects.
*/
def getThings(thingList, includeMe='', Boolean exclude=false) {
  def retVal = []
  def withList = []

  if (includeMe) {
    thingList.each { thing ->
      if (exclude) {
        if (!stringContains (thing.name, includeMe)) {
          retVal << thing
        }
      } else {
        if (stringContains (thing.name, includeMe)) {
          retVal << thing
        }
      }
    }
   } else {  // if (includeMe)
     // return everything
     retVal = thingList
   }
   return retVal
}

/*!
 * Private method that returns true if the needle is found in the haystack, otherwise false.
 *
 * This method returns true if haystack contains any of the strings in the needleList.
 * Adding a '*' or '%' to the beginning or end
 * of the string will match "ends with" and "begins with" respectivly.
*/
Boolean stringContains (String haystack, needle) {

  Boolean retVal = false
  def wildCards = ['*', '%']
  def withList = []
  Integer  wildCardCount   // 1 = leading, 2 = ending, 3 = both

  // if the needle is a string, convert it to a list
  // if needle is a comma seperated string convert it to a list
  // if it is a list just use it
  if (needle.class.toString() == 'class java.util.ArrayList') {
    withList = needle
  } else {
    if (needle.contains(',')) {
      // break the comma seperated string into a list
      withList = needle.split(',')
    } else {  // single value put it in a list
      withList = [needle]
    }
  }

  withList.each { pin ->
    wildCardCount = 0
    if (wildCards.contains(pin[0]))
      wildCardCount += 1
    if (wildCards.contains(pin[-1]))
      wildCardCount += 2

    def haystackUpper = haystack?.toUpperCase()
    def pinUpper = pin.toUpperCase()
    switch (wildCardCount) {
      case 1:  // wild card in fist position
        retVal = retVal || haystackUpper.endsWith (pinUpper[1..-1]?.trim())
        break
      case 2:  // wild card in last position
        retVal = retVal || haystackUpper.startsWith (pinUpper[0..-2]?.trim())
        break
      case 3:  // double wildcard
        retVal = retVal || haystackUpper.contains ((pinUpper?.trim()) - wildCards[0]- wildCards[1])
        break
      default: //exact match
        retVal = retVal || haystackUpper == (pinUpper?.trim())
    }
  }
  return retVal
}
/*!
 * Writes to the log a string containing that command that was evaluated, and the result.
 *
 * Unfortunately it is not yet working in the context of sddm-plus.
 *
 * @param assertion String containg the command to be evaluated
 * @return String containing the command evaluated and the result
 */
def asserter(String assertion) {
	log("$assertion: ${evaluate(assertion)}")
}
