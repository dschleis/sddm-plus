## sddm-plus.groovy

- [log()](#log)
- [show()](#show)
- [ask()](#ask)
- [getTables()](#getTables)
- [getTableWhereNameEquals()](#getTableWhereNameEquals)
- [getTablesWhereNameLike()](#getTablesWhereNameLike)
- [getTablesWhereNameNotLike()](#getTablesWhereNameNotLike)
- [Table.getColumns()](#getColumns)
- [Table.getColumnWhereNameEquals()](#getColumnWhereNameEquals)
- [Table.getColumnsWhereNameLike()](#getColumnsWhereNameLike)
- [Table.getColumnsWhereNameNotLike()](#getColumnsWhereNameNotLike)

## log()<a name='log'/a>

<p>
<p>Writes to the Data Modeler log window.</p>
</p>

### Syntax
```groovy
log(String message)
```

### Parameters
Name | Description
--- | ---
message | The message that is displayed in the log window

## show()<a name='show'/a>

<p>
<p>Displays a message in a dialog box.</p>
</p>

### Syntax
```groovy
show (String message)
```

### Parameters
Name | Description
--- | ---
message | The message that is displayed in the dialog box

## ask()<a name='ask'/a>

<p>
<p>Displays a message in a dialog box and returns the string that is input.</p>
</p>

### Syntax
```groovy
ask (String question)
```

### Parameters
Name | Description
--- | ---
question | The message that is displayed in the dialog box
*return* |  A String object containing the user input


## getTables()<a name="getTables"></a>

<p>
<p>Returns a list of the table objects found in the current relational model.</p>
</p>

### Syntax
```groovy
getTables()
```

### Parameters
Name | Description
--- | ---
*return* | List of Table objects

## getTableWhereNameEquals()<a name="getTableWhereNameEquals"></a>

<p>
<p>Returns the table with the given name (case insensitive) found in the current relational model.</p>
</p>

### Syntax
```groovy
getTableWhereNameEquals(String matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String that is the name of the table
*return* | A Table object

## getTablesWhereNameLike()<a name="getTablesWhereNameLike"/>

<p>
<p>Returns a List of Table objects from the current relational model that match the given matcher (case insensitive).</p>
<p>This method compares the table names with the matcher and returns those table
objects that match.</p>
<p>The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.</p>

- A simple string: 'test'
- A string contining a list of comma-seperated values: 'abc, 123, *test'
- A Groovy List of strings: ['abc', '%123', 'test*']

### Syntax
```groovy
getTablesWhereNameLike(matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String or List
*return* | A List of Table objects

## getTablesWhereNameNotLike()<a name="getTablesWhereNameNotLike"/>

<p>
<p>Returns a List of Table objects from the current relational model that do not match the given matcher (case insensitive).</p>
<p>This method compares the table names with the matcher and returns those table
objects that match.</p>
<p>The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.</p>

- A simple string: 'test'
- A string contining a list of comma-seperated values: 'abc, 123, *test'
- A Groovy List of strings: ['abc', '%123', 'test*']
</p>

### Syntax
```groovy
getTablesWhereNameNotLike(matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String or List
*return* | A List of Table objects

## Table.getColumns()<a name="getColumns"/>

<p>
<p>Returns all columns of a table.</p>
</p>

### Syntax
```groovy
getColumns()
```

### Example
```groovy
getTables().each {table ->
  log ("Table: ${table.name}")
  table.getColumns().each {col ->
    log ("  ${col.name}")
  }
}
```

### Parameters
Name | Description
--- | ---
*return* | List of Column objects

## Table.getColumnWhereNameEquals()<a name="getColumnWhereNameEquals"/>

<p>
<p>Returns the column of a table with the given name (case insensitive)</p>
</p>

### Syntax
```groovy
getColumnWhereNameEquals(String matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String that is the name of the column
*return* | A Column object.

## Table.getColumnsWhereNameLike()<a name="getColumnsWhereNameLike"/>

<p>
<p>Returns a List of Column objects that match the given matcher (case insensitive).</p>
<p>This method compares the column names with the matcher and returns those column objects that match.</p>
<p>The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.</p>

- A simple string: 'test'
- A string contining a list of comma-seperated values: 'abc, 123, *test'
- A Groovy List of strings: ['abc', '%123', 'test*']
</p>

### Syntax
```groovy
Table.getColumnsWhereNameLike(matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String or List
*return* | A List of Column objects

## Table.getColumnsWhereNameNotLike()<a name="getColumnsWhereNameNotLike"/>

<p>
<p>Returns a List of Column objects that do not match the given matcher (case insensitive).</p>
<p>This method compares the column names with the matcher and returns those column objects that do not match.</p>
<p>The matcher is case insensitive and recognizes wildcards of '*' and '%' at the beginning or end and can take several forms.</p>

- A simple string: 'test'
- A string contining a list of comma-seperated values: 'abc, 123, *test'
- A Groovy List of strings: ['abc', '%123', 'test*']
</p>

### Syntax
```groovy
Table.getColumnsWhereNameNotLike(matcher)
```

### Parameters
Name | Description
--- | ---
matcher | A String or List
*return* | A List of Column objects
