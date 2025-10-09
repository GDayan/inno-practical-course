# CustomLinkedList

A custom implementation of a doubly-linked list in Java that provides efficient list operations with optimized element access.

## Features

- **Doubly-linked structure**: Each node maintains references to both next and previous nodes
- **Optimized access**: Uses intelligent traversal (from head or tail) for index-based operations
- **Type safety**: Generic implementation supporting any object type
- **Comprehensive operations**: Full set of list manipulation methods
- **Exception handling**: Proper error handling for edge cases
- **Comprehensive testing**: Complete unit test coverage with JUnit 5

## Operations

### Basic Information
- `size()` - Returns the number of elements in the list

### Addition Operations
- `addFirst(T element)` - Inserts element at the beginning
- `addLast(T element)` - Appends element to the end
- `add(int index, T element)` - Inserts element at specified position

### Retrieval Operations
- `getFirst()` - Returns the first element
- `getLast()` - Returns the last element
- `get(int index)` - Returns element at specified position

### Removal Operations
- `removeFirst()` - Removes and returns the first element
- `removeLast()` - Removes and returns the last element
- `remove(int index)` - Removes and returns element at specified position
