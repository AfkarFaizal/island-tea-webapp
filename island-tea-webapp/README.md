# Island Tea Co. & Ceylon Coffee Club — Website

A Spring Boot (Java 17) website for **Island Tea Co. & Ceylon Coffee Club — Malabe**,
built to explicitly demonstrate object-oriented design and core data
structures & algorithms rather than hiding everything behind framework
magic.

## Run it

Requires Java 17+ and Maven, with access to Maven Central (this sandbox's
network doesn't allow that domain, so the build couldn't be verified here —
run it locally):

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080]

## Project layout

```text
com.islandtea
├── model/            OOP domain model
├── datastructures/   Hand-written DSA building blocks
├── service/          Business logic (uses the DSA layer)
└── controller/        Spring MVC controllers (Thymeleaf pages + a small REST API)
```

## OOP concepts, and where to find them

| Concept | Where |
| --- | --- |
| Abstraction (interface) | `model/Reviewable.java` — contract for anything that produces a rating + text |
| Abstract class | `model/Person.java`, `model/Reviewer.java` |
| Inheritance | `Person → Reviewer → LocalGuideReviewer / RegularReviewer` |
| Polymorphism | `Reviewer.getTrustWeight()` — `ReviewService` ranks reviews without knowing the concrete subtype |
| Encapsulation | All model fields are `private`/`final`, exposed only via getters |
| Builder pattern | `model/Cafe.java` (`Cafe.Builder`) — immutable object, readable construction |
| Singleton pattern (Spring-idiomatic) | `service/CafeInfoProvider.java` — one bean, one shared `Cafe` record |
| `Comparable` for natural ordering | `Person` (by name), `MenuItem` (by price) |

## DSA concepts, and where to find them

| Concept | Where | Why |
| --- | --- | --- |
| Custom singly linked list | `datastructures/CustomLinkedList.java` | Backs the review timeline in insertion order |
| Trie (prefix tree) | `datastructures/MenuTrie.java` | Powers `/api/menu/search?prefix=` autocomplete on the menu page |
| Merge sort (generic, from scratch) | `datastructures/SortUtils.mergeSort` | Sorts menu items by price |
| Binary search (generic, from scratch) | `datastructures/SortUtils.binarySearch` | Exact-price lookups over the sorted menu |
| Heap / `PriorityQueue` (top-K pattern) | `service/ReviewService.getTopReviews` | Extracts the top-N reviews by weighted score in `O(n log k)` |
| Hash map for O(1) grouping | `service/MenuService` (`Map<MenuCategory, List<MenuItem>>`) | Category lookups without re-filtering the whole menu |
| Stack (`ArrayDeque` as LIFO) | `service/MenuService` recently-viewed items | Most-recent-first browsing history |

## Pages

- `/` — home, hero, signature items, top reviews
- `/menu` — full menu grouped by category, live Trie-backed search
- `/menu/item/{id}` — single item detail (also records a "recently viewed" stack push)
- `/reviews` — top reviews (heap-ranked) + full chronological timeline
- `/about` — address, contact, amenities, sourced from the Google Maps listing

## Data source

Cafe details, address, phone, rating and the three featured reviews (Dil,
Nirmani Samarakoon, Nuwan Wijesinghe) were seeded from the Google Maps
listing you provided, in `service/CafeDataInitializer.java`. Menu items are
illustrative Ceylon tea / coffee / breakfast placeholders — swap in the
real menu by editing that same file (or wiring `MenuService` to a database
later; the class is already structured so JPA repositories could replace
the in-memory `List` without touching controllers or templates).

## Extending this into production

- Swap in-memory seeding for a real database (Spring Data JPA + Postgres/MySQL) — the service layer already shields controllers from the storage detail.
- Add a `staff`/`admin` role to `Person`'s hierarchy for menu management.
- Cache the price-sorted menu list instead of re-sorting on every binary search call.
