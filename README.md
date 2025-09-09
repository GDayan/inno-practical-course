# Robot Battle Simulation

## Project Description

This project simulates a competition between two factions — **World** and **Wednesday** — to build the strongest army of robots.

The robots are assembled from parts produced by a neutral **Factory**, which generates parts randomly each day.

---

## Simulation Rules

1. **Robot Parts**:
    - `HEAD`
    - `TORSO`
    - `HAND`
    - `FEET`

2. **Factory**:
    - Produces up to **10 random parts per day**.
    - Operates as a separate thread in the simulation.
    - Supplies parts to factions at night.

3. **Factions**:
    - Each faction can carry **up to 5 parts per night** from the Factory.
    - Robots can only be assembled when a complete set of parts is available.
    - Each faction operates in its **own thread**.

4. **Simulation Duration**:
    - The simulation runs for **100 days**.

5. **Objective**:
    - Determine which faction builds the **strongest army** (most fully assembled robots) after 100 days.
