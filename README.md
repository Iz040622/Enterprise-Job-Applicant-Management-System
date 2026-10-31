# Enterprise Job & Applicant Management System (Java ADT) 💼🌳

[![Language](https://img.shields.io/badge/Language-Java%20SE%2017+-orange.svg)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-Custom%20ADT%20(Red--Black%20Tree)-blue.svg)]()
[![UI](https://img.shields.io/badge/GUI-Java%20Swing%20%2F%20AWT-red.svg)]()

A high-performance Java enterprise console and analytical system built for managing multi-tier recruitment workflows, applicant credentials, and employer job listings. 

Developed without reliance on Java's built-in `java.util.TreeSet` or `java.util.TreeMap`, this project implements a **custom, generic, self-balancing Red-Black Binary Search Tree ADT** from first principles, guaranteeing $O(\log n)$ performance across all data operations.

---

## 🧠 Core Algorithmic Architecture

### Custom Red-Black TreeSet Abstract Data Type (`TreeSet.java`)
* **Self-Balancing Invariants:** Strictly adheres to Red-Black tree properties (root/leaves are black, red nodes cannot have red children, equal black-height across all root-to-null paths).
* **Insertion & Deletion Balancing:** Features custom `rotateLeft()`, `rotateRight()`, and color-flip fix-up methods (`fixInsertion`, `fixDeletion`) to maintain balance invariants during live updates.
* **Subtree Size Augmentation:** Each node maintains an internal `size` attribute dynamically updated during tree mutations, enabling $O(\log n)$ index-based retrieval (`get(int index)`).
* **Mathematical Set Operations:** Native implementations of `union()`, `intersect()`, `subset()`, `headSet()`, and `tailSet()`.
* **Fail-Safe Iterators:** Custom in-order traversal iterators implementing `java.util.Iterator<T>`.

---

## 👤 My Subsystem Contributions: Applicant Management & Analytics

As the lead developer for the **Applicant Management Subsystem**, my modules govern candidate authentication, profile lifecycle management, and visual analytics:

1. **Authentication & RBAC Control (`Authentication.java`):**
   * Multi-role validation separating applicants from corporate employers.
   * Regex-driven security enforcement for usernames and complex passwords.
2. **Account Maintenance (`AccountMaintenance.java`):**
   * Full profile updates (soft skills, academic qualifications, CGPA scoring, technical skill set mappings).
   * Cascading application state updates and safe deletion workflows.
3. **Statistical Visualizations (`ApplicationStatsControl.java` & Swing Boundary):**
   * Aggregates applicant pipelines by application date (`yyyy-MM`) and review status (Pending, Approved, Rejected).
   * Renders dynamic, real-time graphical reporting engines using custom **Java Swing & AWT Graphics2D** (pie charts and distribution bar charts).

---

## 📂 System Package Structure

```
src/main/java/
├── adt/               # Custom Data Structures (SetInterface, TreeSet, MapInterface, HashMap)
├── boundary/          # Console UI & Swing Graphics Panels (ApplicantKeywordPieChartUI)
├── control/           # Business Logic Controllers
│   ├── Authentication.java             # Credential verification & role separation
│   ├── AccountMaintenance.java         # Applicant profile lifecycle management
│   ├── ApplicationStatsControl.java    # Application reporting & data aggregation
│   └── JobMatchingService.java         # Candidate-to-Job compatibility scoring engine
├── entity/            # Domain Entities (Applicant, Employer, Job, Application)
└── utility/           # String validation & formatting helpers
```

---

## 🚀 Execution & Running

### Prerequisites
* Java Development Kit (JDK 17 or higher)

### Build & Run
```bash
# Clone the repository
git clone https://github.com/Iz040622/Enterprise-Job-Applicant-Management.git
cd Enterprise-Job-Applicant-Management

# Compile the source files
javac -d bin src/main/java/adt/*.java src/main/java/entity/*.java src/main/java/boundary/*.java src/main/java/control/*.java src/main/java/utility/*.java

# Execute the application
java -cp bin boundary.MainAppUI
```

---

## 👨‍💻 Project Team
* **Isaac Lim Tian Le** - *Applicant Management Subsystem, Statistical Visualizations, Custom ADT Co-Author*
* **Lau Aik Hung** - *Job Management Subsystem, Employer Control*
* **Chin Jia Jun** - *Matching Engine Subsystem, Core TreeSet ADT*
* **Keshandra A/L Jayaselan** - *Levenshtein Distance Search Subsystem*