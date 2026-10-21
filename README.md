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

```bash
=====================
 Zombie Apocalypse - Simulation
=====================

Survivors: 10
Zombies: 44

--- Battle Begins ---

>>> DAY 1 <<<
Julia vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 10 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Diamond vs Zombie (HP 4)
 Zombie defeated!
 HP: 7/15 | Shells: 5 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Amy vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 10 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Bron vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 11 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Chris vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 9 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Leo vs Zombie (HP 4)
 Zombie defeated!
 HP: 5/15 | Shells: 4 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Kong vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 11 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Peter vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 10 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Chue vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 9 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Billy vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 10 | Ammo:25 | Food:9 | Armor: 3 | Medkits:2 | Knife:yes

Status after Day 1:
 Living people: 10
 Living zombies: 34

>>> DAY 2 <<<
 Julia eats for healing HP: 15
 Diamond eats for healing HP: 9
 Chris eats for healing HP: 13
 Leo eats for healing HP: 7
 Chue eats for healing HP: 15
 Diamond uses a medkit! HP: 14
 Chris uses a medkit! HP: 15
 Leo uses a medkit! HP: 12
Julia vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 8 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Diamond vs Zombie (HP 4)
 Zombie defeated!
 HP: 14/15 | Shells: 4 | Ammo:25 | Food:8 | Armor: 3 | Medkits:1 | Knife:yes

Amy vs Zombie (HP 4)
 Zombie defeated!
 HP: 9/15 | Shells: 4 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Bron vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 9 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Chris vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 8 | Ammo:25 | Food:8 | Armor: 3 | Medkits:1 | Knife:yes

Leo vs Zombie (HP 4)
 Zombie defeated!
 HP: 8/15 | Shells: 1 | Ammo:25 | Food:8 | Armor: 3 | Medkits:1 | Knife:yes

Kong vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 10 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Peter vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 6 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Chue vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 5 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Billy vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 8 | Ammo:25 | Food:8 | Armor: 3 | Medkits:2 | Knife:yes

Status after Day 2:
 Living people: 10
 Living zombies: 24

>>> DAY 3 <<<
 Julia eats for healing HP: 15
 Diamond eats for healing HP: 15
 Amy eats for healing HP: 11
 Leo eats for healing HP: 10
 Peter eats for healing HP: 13
 Chue eats for healing HP: 13
 Amy uses a medkit! HP: 15
 Leo uses a medkit! HP: 15
 Peter uses a medkit! HP: 15
 Chue uses a medkit! HP: 15
Julia vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 5 | Ammo:25 | Food:7 | Armor: 3 | Medkits:2 | Knife:yes

Diamond vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 1 | Ammo:25 | Food:7 | Armor: 3 | Medkits:1 | Knife:yes

Amy vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 2 | Ammo:25 | Food:7 | Armor: 3 | Medkits:1 | Knife:yes

Bron vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 8 | Ammo:25 | Food:7 | Armor: 3 | Medkits:2 | Knife:yes

Chris vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 7 | Ammo:25 | Food:7 | Armor: 3 | Medkits:1 | Knife:yes

Leo vs Zombie (HP 4)
 Leo has died!

Kong vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 7 | Ammo:25 | Food:7 | Armor: 3 | Medkits:2 | Knife:yes

Peter vs Zombie (HP 4)
 Zombie defeated!
 HP: 13/15 | Shells: 4 | Ammo:25 | Food:7 | Armor: 3 | Medkits:1 | Knife:yes

Chue vs Zombie (HP 4)
 Zombie defeated!
 HP: 11/15 | Shells: 2 | Ammo:25 | Food:7 | Armor: 3 | Medkits:1 | Knife:yes

Billy vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 7 | Ammo:25 | Food:7 | Armor: 3 | Medkits:2 | Knife:yes

Status after Day 3:
 Living people: 9
 Living zombies: 15

.............

>>> DAY 6 <<<
 Chris eats for healing HP: 15
Diamond vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 0 | Ammo:19 | Food:4 | Armor: 3 | Medkits:0 | Knife:yes

Chris vs Zombie (HP 4)
 Chris has died!

Peter vs Zombie (HP 4)
 Zombie defeated!
 HP: 15/15 | Shells: 0 | Ammo:25 | Food:4 | Armor: 3 | Medkits:1 | Knife:yes

Status after Day 6:
 Living people: 3
 Living zombies: 0

========================
 FINAL RESULT 
========================

THE PEOPLE SURVIVED!
Survivors remaining: 3
 -Diamond [Health: 15, Ammo: 19, Food: 4, Medkits: 0, Armor: 3, Knife: YES, ALIVE]
 -Peter [Health: 15, Ammo: 25, Food: 4, Medkits: 1, Armor: 3, Knife: YES, ALIVE]
 -Billy [Health: 15, Ammo: 25, Food: 4, Medkits: 2, Armor: 3, Knife: YES, ALIVE]

========================
 DETAILED PERSON REPORT
========================

--- Julia ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 21
 Final Food: 5
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 4 missed: 8)
 Shots fired: 4 (hit: 1 missed: 3)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 23 (while shooting:23,  while in melee: 0)
 Food eaten: 2 (normal) + 3 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 15

--- Diamond ---
 Status: SURVIVED
 Final HP: 15/15
 Final Shells: 0
 Final Ammo: 19
 Final Food: 4
 Final Armor: 3
 Final Medkits: 0
 Knife: YES
 Shotgun shells fired: 12 (hit: 3 missed: 9)
 Shots fired: 6 (hit: 4 missed: 2)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 14 (while shooting:14,  while in melee: 0)
 Food eaten: 2 (normal) + 4 (healing)
 Outcome:  Made it out alive!

--- Amy ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 23
 Final Food: 5
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 4 missed: 8)
 Shots fired: 2 (hit: 1 missed: 1)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 23 (while shooting:23,  while in melee: 0)
 Food eaten: 3 (normal) + 2 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 13

--- Bron ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 19
 Final Food: 5
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 3 missed: 9)
 Shots fired: 6 (hit: 2 missed: 4)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 22 (while shooting:22,  while in melee: 0)
 Food eaten: 4 (normal) + 1 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 14

--- Chris ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 23
 Final Food: 4
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 5 missed: 7)
 Shots fired: 2 (hit: 0 missed: 2)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 21 (while shooting:21,  while in melee: 0)
 Food eaten: 4 (normal) + 2 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 9

--- Leo ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 24
 Final Food: 7
 Final Armor: 3
 Final Medkits: 0
 Knife: YES
 Shotgun shells fired: 12 (hit: 2 missed: 10)
 Shots fired: 1 (hit: 0 missed: 1)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 29 (while shooting:29,  while in melee: 0)
 Food eaten: 1 (normal) + 2 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 13

--- Kong ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 24
 Final Food: 5
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 4 missed: 8)
 Shots fired: 1 (hit: 0 missed: 1)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 24 (while shooting:24,  while in melee: 0)
 Food eaten: 3 (normal) + 2 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 14

--- Peter ---
 Status: SURVIVED
 Final HP: 15/15
 Final Shells: 0
 Final Ammo: 25
 Final Food: 4
 Final Armor: 3
 Final Medkits: 1
 Knife: YES
 Shotgun shells fired: 12 (hit: 6 missed: 6)
 Shots fired: 0 (hit: 0 missed: 0)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 6 (while shooting:6,  while in melee: 0)
 Food eaten: 4 (normal) + 2 (healing)
 Outcome:  Made it out alive!

--- Chue ---
 Status: DEAD
 Final HP: 0/15
 Final Shells: 0
 Final Ammo: 19
 Final Food: 5
 Final Armor: 3
 Final Medkits: 0
 Knife: YES
 Shotgun shells fired: 12 (hit: 4 missed: 8)
 Shots fired: 6 (hit: 1 missed: 5)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 27 (while shooting:27,  while in melee: 0)
 Food eaten: 1 (normal) + 4 (healing)
 Death cause: killed by zombie (gun-miss critical)
 Damage at death: 15

--- Billy ---
 Status: SURVIVED
 Final HP: 15/15
 Final Shells: 3
 Final Ammo: 25
 Final Food: 4
 Final Armor: 3
 Final Medkits: 2
 Knife: YES
 Shotgun shells fired: 9 (hit: 5 missed: 4)
 Shots fired: 0 (hit: 0 missed: 0)
 Knife swings: 0 (hit: 0 missed: 0, kills: 0)
 Damage taken: 2 (while shooting:2,  while in melee: 0)
 Food eaten: 5 (normal) + 1 (healing)
 Outcome:  Made it out alive!

=====================
```
## Reflection

The hardest part of this project was not writing the code. It was recognizing when the code, despite following the rule was failing at the assignment's purpose.

