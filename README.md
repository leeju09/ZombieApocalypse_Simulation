# Zombie Apocalypse Simulation

A turn-based zombie survival simulation written in Java. Survivors fight off a zombie horde using a shotgun, pistol, knife, or their fists — managing ammo, food, medkits, armor, and dodge along the way. The simulation runs until either all survivors are dead or the entire zombie horde has been defeated.

Built for **Unit 6: Real-World Modeling with Custom Classes**.



Assignment at North Hennepin Community College

Course: CSCI 2001-52 Object Oriented Programming

Fall Semester 2026

---

## Table of Contents

- Overview
- Project Structure
- Game Mechanics
- Design Decisions
- Class Responsibilities
- Sample Output
- Reflection

---

## Overview

The simulation creates an array of `Person` objects (survivors) and an array of `Zombie` objects (the horde). Each round, every living survivor:

1. Eats food (either to heal or to survive)
2. Uses a medkit if injured
3. Fights one zombie using the best available weapon

The round repeats until one side is wiped out. A detailed report at the end shows each survivor's final stats, weapon usage, damage taken, food eaten, and (for the dead) the cause of death.

---
## Project Structure

├── Battle.java       # Combat logic (weapon dispatch, damage, dodge, armor)

├── GameConfig.java   # All game constants (probabilities, damage values)

├── Main.java         # Simulation entry point and battle loop

├── Person.java       # Survivor state and stats tracking

├── RandomUtil.java   # Random number helpers

├── Zombie.java       # Zombie state

└── README.md

## Class Responsibilities (SRP)
Class	Responsibility
 - **Person**	 - Stores survivor state (HP, ammo, food, etc.) and stats. No combat logic.
 - **Zombie**	 - Stores zombie state (HP, living status). No combat logic.
 - **Battle**	 - Applies combat rules. Modifies Person/Zombie state but stores nothing itself.
 - **GameConfig**	 - Holds constants only. No methods, no logic.
 - **RandomUtil**	 - Handles randomness. Keeps probability logic separate from combat.
 - **Main**	 - Controls the flow. Does not store state or contain combat logic.

## Game Mechanics

Survivor Starting Stats

**Stat**	 &  **Value**

- Health	       - 15

- Armor	         - 3 (reduces incoming damage, floor 0)

- Dodge	         - 1-in-3 chance to avoid any hit

- Pistol         - Ammo	25 rounds

- Shotgun        - Shells	12

- Food	         - 10 units

- Medkits	       - 2 (each heals 5 HP)

## Weapons

Survivors use weapons in this priority order:

  - Shotgun — 1-in-3 hit, instant kill on hit, 5 damage on miss

  - Pistol — 1-in-5 hit, 2 damage (1-in-3 chance of instant kill)

  -  Knife — 1-in-2 hit, instant kill on hit

  -  Fists — 1-in-3 hit, 1 damage (1-in-10 chance of instant kill)

On a miss, the zombie retaliates. The survivor gets one dodge roll first; if the dodge fails, the zombie's retaliation applies (damage reduced by armor).

## Zombies

- 25 to 60 zombies per simulation

- 4 HP each

- Killed by 2 non-critical pistol hits, 1 critical pistol hit, or any shotgun/knife hit

## Food & Healing

- Each survivor consumes 1 food per round for survival

- If injured, a survivor may instead eat food for healing (+2 HP)

- If out of food, a survivor takes 1 starvation damage per round

- Medkits heal 5 HP each and are used automatically between rounds when injured

## Win Condition
- Survivors win when all zombies are dead

OR

- Zombies win when all survivors are dead

## Design Decisions
1. Instant Death Replaced with Damage
    -  The original specification says:

    -  A gun miss → 50% chance of instant death

    -  A melee miss → instant death

    -  I followed the spec exactly first. Everyone died by Day 4. The 40%-per-shot death rate meant no survivor ever ran out of ammo, used a medkit, healed with food, or switched to melee.

    -  Change made: Gun-miss critical → 5 damage. Melee miss → 2 damage. This preserves combat risk while allowing all the other systems (armor, medkits, food, weapon swapping) to actually function.

    -  This deviation is documented in a comment at the top of Battle.java.

2. Single fight() Dispatcher
    -  Instead of exposing gunBattle and meleeBattle as separate public methods, Battle.fight() is the single entry point. It automatically picks the best available weapon (shotgun → pistol → knife → fists). The internal weapon methods are private.

4. Zombie Count 25–60
    -  The spec suggests 15–45. I used 25–60 to make fights last longer and create more meaningful combat.

6. Extensions Added
    - Per the spec's "encouraged extras" section, I added: shotgun, knife, armor, dodge, medkits, food healing, and full stat tracking.

## Sample Output
[Open the text file](path/to/ZombieSims_text.txt)
## Reflection

The hardest part of this project was not writing the code. It was recognizing when the code, despite following the rule was failing at the assignment's purpose.

