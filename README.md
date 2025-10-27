# Particle Collision Simulator

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)](https://www.java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> Real-time 2D physics simulation demonstrating elastic collisions, gravity, friction, and momentum conservation

A physics engine built in Java that simulates realistic particle interactions with visual trails and interactive controls. Watch colorful particles bounce, collide, and interact following real physics principles!

---

## 🎬 Demo
![2025-10-2719-35-37-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/48041532-3856-4570-b6e6-1c59c8b0c5b3)

---

## ✨ Features

### Physics Simulation
- **⚛️ Elastic Collisions** - Momentum and energy conservation between particles
- **🌍 Gravity Simulation** - Toggle between Earth gravity and zero-gravity space mode
- **📉 Friction Modeling** - Both kinetic and static friction with energy dissipation
- **⚖️ Mass-Based Interactions** - Realistic physics based on particle mass and velocity
- **💥 Energy Loss** - Configurable energy dissipation on collisions

### Visual Effects
- **🌈 Colorful Particles** - Each particle gets a random color
- **✨ Motion Trails** - Beautiful trails that follow particle movement
- **💫 Particle Effects** - Spark effects on high-velocity collisions (optional)
- **🎯 Hitbox Visualization** - Toggle to see collision boundaries

### Interactive Controls
- **➕➖ Add/Remove Particles** - Dynamically spawn or remove particles
- **⏸️ Pause/Resume** - Freeze the simulation at any time
- **⚡ Speed Control** - Speed up or slow down the simulation
- **🚀 Boost Mode** - Give all particles a random velocity boost
- **🔍 Debug View** - Toggle hitbox visualization

---

## 🎮 Controls

| Key | Action |
|-----|--------|
| `Q` | Add a new particle |
| `R` | Remove a particle |
| `P` | Pause/Resume simulation |
| `E` | Give all particles a random boost |
| `L` | Speed up simulation (+10 ticks/sec) |
| `K` | Slow down simulation (-10 ticks/sec) |
| `H` | Cycle hitbox visualization (none → circular → rectangular) |

---

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, VS Code) or command line

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/collision-simulator.git
   cd collision-simulator
   ```

2. **Compile**
   ```bash
   javac -d bin src/program/*.java src/Input/*.java
   ```

3. **Run**
   ```bash
   java -cp bin program.Driver
   ```

### Quick Start (IDE)

1. Import the project into your IDE
2. Run `Driver.java` as a Java Application
3. Start experimenting with the controls!

---

## 🔧 Configuration

Edit constants in `DrawFrame.java` to customize the simulation:

```java
// Enable/disable space mode (no gravity)
public final static boolean inSpace = false;

// Enable/disable energy loss on collisions
public final static boolean energyLoss = true;

// Enable/disable particle effect sparks
public final static boolean particleSystemEnabled = false;

// Maximum number of particles allowed
public final static int MAX_PARTICLES = 100;
```

In `Particle.java`:

```java
// Gravity acceleration constant
public final static float GRAVITY_ACCELERATION = 0.1f;

// Friction coefficients
public final static float MUIX = 0.2f;  // Kinetic friction X
public final static float MUIY = 0.2f;  // Kinetic friction Y
public final static float MUSX = 0.4f;  // Static friction X
public final static float MUSY = 0.4f;  // Static friction Y
```

---

## 📐 Physics Model

### Collision Detection
- Uses circular hitbox detection for accurate collision detection
- Distance-based algorithm: collision occurs when `distance < radius1 + radius2`

### Momentum Conservation
```
m₁v₁ + m₂v₂ = m₁u₁ + m₂u₂
```
Where:
- `m` = mass
- `v` = initial velocity
- `u` = final velocity

### Elastic Collision Equations
```java
// Final velocity of particle 1
u1 = ((m1-m2)*v1 + 2*m2*v2) / (m1+m2)

// Final velocity of particle 2
u2 = (2*m1*v1 - (m1-m2)*v2) / (m1+m2)
```

### Energy Dissipation
- **Kinetic Friction**: `F = μₖ × m × g × v`
- **Static Friction**: Prevents motion when kinetic energy < friction threshold
- **Wall Collisions**: Additional friction coefficient for boundaries

---

## 🏗️ Project Structure

```
collision-simulator/
├── src/
│   ├── program/
│   │   ├── Driver.java           # Entry point
│   │   ├── DrawFrame.java        # Main game loop & rendering
│   │   ├── Window.java           # JFrame window setup
│   │   ├── Particle.java         # Main particle physics
│   │   ├── ParticleEffect.java   # Collision effect particles
│   │   ├── Position.java         # 2D position class
│   │   ├── Trail.java            # Motion trail system
│   │   └── TrailParticle.java    # Individual trail particles
│   └── Input/
│       ├── KeyInput.java         # Keyboard controls
│       └── MouseInput.java       # Mouse input (extensible)
└── README.md
```

---

## 🎓 Educational Value

This simulator demonstrates fundamental physics concepts:

- **Classical Mechanics**: Newton's laws of motion
- **Conservation Laws**: Momentum and energy conservation
- **Collision Theory**: Elastic vs inelastic collisions
- **Friction**: Static and kinetic friction models
- **Numerical Integration**: Real-time physics simulation
- **Computational Physics**: Discrete time-step simulation

Perfect for:
- Physics students learning collision mechanics
- Game developers studying physics engines
- Anyone interested in computational physics

---

## 🛠️ Technical Details

### Built With
- **Language**: Java
- **Graphics**: Java AWT/Swing
- **Game Loop**: Custom tick-render cycle at 120 TPS

### Performance
- Runs at 120 ticks per second
- Supports up to 100 particles simultaneously
- Uses BufferStrategy for smooth rendering
- Iterative collision resolution (3 iterations per frame)

### Algorithms
- **Collision Detection**: O(n²) all-pairs checking
- **Collision Response**: Vector-based separation
- **Trail System**: Linked list with fixed capacity
- **Particle Effects**: Fading alpha transparency

---

## 🐛 Known Limitations

- Collision detection is O(n²) - performance decreases with many particles
- Particles can occasionally overlap slightly under extreme conditions
- No spatial partitioning (quadtree/grid) for optimization
- Fixed simulation rate (requires modern CPU)

---

## 🚧 Future Enhancements

- [ ] Add spatial partitioning for better performance
- [ ] Implement different particle shapes (squares, triangles)
- [ ] Add particle attraction/repulsion forces
- [ ] Create preset scenarios (orbital motion, Newton's cradle)
- [ ] Add statistics display (total energy, momentum, collisions/sec)
- [ ] Export simulation data for analysis
- [ ] Mouse-based particle spawning and interaction
- [ ] Save/load simulation states

---

## 📝 Physics References

This simulation is based on:
- Conservation of momentum in elastic collisions
- Classical Newtonian mechanics
- Coulomb friction model
- Standard collision response algorithms

---

## 🎓 Development Journey

This simulator was built during my **first year of college** as a project to:
- Deepen understanding of classical mechanics
- Learn computational physics methods
- Explore game engine development
- Practice numerical simulation techniques

It represents the progression from basic programming to understanding how physics engines actually work under the hood.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Taher Joudeh**
- GitHub: [@TaherJoudeh](https://github.com/TaherJoudeh)

**Project Timeline:**
- **Square** (11th grade) - Game development fundamentals
- **Collision Simulator** (College freshman) - Physics engine development

---

## 🙏 Acknowledgments

- Built to understand physics engine development
- Inspired by classic particle simulation demonstrations
- Educational project exploring computational physics
- Part of learning journey from game development to engine development

---

## ⭐ Support

If you found this simulator interesting or useful for learning:
- Give it a star ⭐
- Fork it and experiment 🍴
- Share with physics/CS students 📚
- Open issues for bugs or questions 🐛

---

<p align="center">
<strong>Made with ☕ and curiosity about physics | First year at college</strong><br>
Simulating the universe, one collision at a time! ⚛️
</p>
