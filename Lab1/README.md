# Lab 1 – Object-Oriented Concepts

This lab explores the basics of object-oriented programming using a simple library system, with program structure and design choices prioritizing readability, understandability, and maintainability.

## Overview & What Was Done

The code models a library containing books and people (students, faculty). Each concept is represented as a class in `src/`. Here’s a quick rundown:

- **Book:** Represents a library book, tracking its details and availability.
- **Person:** The base class for everyone who interacts with the library, extended by:
  - **Student**
  - **Faculty**
- **Library:** Manages books and people, providing borrowing and returning operations.
- **App:** The entry point that runs some demo code or a basic interface.

You can find a simple UML diagram (`uml.jpeg`) to visualize relationships.

## Class Relationships

- `Student` and `Faculty` both **extend** `Person`, gaining its common attributes (like name).
- `Library` aggregates collections of `Book`s and `Person`s, orchestrating their interactions.

## Principles Followed

- **Readability:** Names are as clear as possible; logic is broken into bite-sized methods.
- **Understandability:** The system mirrors the real world (person, book, library).
- **Maintainability:** Classes have focused responsibilities. Expansion (e.g., adding more `Person` types) would require minimal changes.

Feel free to browse the code and the UML diagram to see how it all fits together. I aimed for code that’s not just functional, but also pleasant for you (and future me!) to read and extend.

– safwansatil
230042117
