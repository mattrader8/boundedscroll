# Bounded Scroll Abstraction

## Overview

This repository contains the source code for a **bounded scroll abstraction** developed as part of a **Software Engineering course** completed during my master’s program at Virginia Tech in **2022**. A bounded scroll is a data structure that stores its elements in a sequence, keeping track of a distinguished position called the "cursor."

The project was designed to demonstrate foundational and intermediate concepts in object-oriented programming, including encapsulation, abstraction, inheritance, and polymorphism. While created in an academic setting, the abstraction reflects real-world architectural considerations and development workflows.

**Purpose of this repository**:<br>
This project is maintained on GitHub as a **reference implementation** showcasing my approach to object-oriented software engineering design and development. Note that evironment-specific configuration is required to run locally.

## Project Goals
The primary goals of this project were to:
- Design and implement a functional abstraction that hides general implementation details
- Implement multiple variations of a data structure (i.e., polymorphism)
- Verify solutions through Java unit testing (i.e., JUnit)
- Explore key data structure concepts (e.g., state, operations)

## Key Features
 - An interface that defines a set of methods for the bounded scroll (e.g., insert, delete, advance, etc.)
 - An abstract class that implements several of the bounded scroll interface methods for general purpose use
 - An abstract class that implements Java's ListIterator interface to help with traversing the bounded scroll
 - Three implementations of the bounded scroll (i.e., StackScroll, ListScroll, and LinkedScroll)
 - JUnit tests for the bounded scroll and scroll iterator implementations
