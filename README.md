# Zombie Apocalypse Simulation

A turn-based zombie survival simulation written in Java. Survivors fight off a zombie horde using a shotgun, pistol, knife, or their fists — managing ammo, food, medkits, armor, and dodge along the way. The simulation runs until either all survivors are dead or the entire zombie horde has been defeated.

Built for **Unit 6: Real-World Modeling with Custom Classes**.

Assignment at North Hennepin Community College
Course: CSCI 2001-52 Object Oriented Programming
Fall Semester 2026

---

## Table of Contents

- [Overview](#overview)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [Game Mechanics](#game-mechanics)
- [Design Decisions](#design-decisions)
- [Class Responsibilities](#class-responsibilities)
- [Sample Output](#sample-output)
- [Reflection](#reflection)

---

## Overview

The simulation creates an array of `Person` objects (survivors) and an array of `Zombie` objects (the horde). Each round, every living survivor:

1. Eats food (either to heal or to survive)
2. Uses a medkit if injured
3. Fights one zombie using the best available weapon

The round repeats until one side is wiped out. A detailed report at the end shows each survivor's final stats, weapon usage, damage taken, food eaten, and (for the dead) the cause of death.

---
Project Structure
.

├── Battle.java       # Combat logic (weapon dispatch, damage, dodge, armor)

├── GameConfig.java   # All game constants (probabilities, damage values)

├── Main.java         # Simulation entry point and battle loop

├── Person.java       # Survivor state and stats tracking

├── RandomUtil.java   # Random number helpers

├── Zombie.java       # Zombie state

└── README.md


