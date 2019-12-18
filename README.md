#  Endless Feed Challenge

## Description

This is a simple app that shows an endless feed of items. Each item is represented as the time of its creation. The feed generates a new item every second and indefinitely spans into the past. The items are arranged in descending order from newest at the top to oldest at the bottom.

### Key Classes
  - `FeedActivity` RecyclerView that shows the feed.
  - `FeedService` Provides an API to get newer/older items after/before a given date. There is an artificial delay intended to emulate network latency.

### Limitations
  - Don't modify `FeedService` or `FeedItem`
  - To maximize responsiveness you should be prefetching items that are likely to appear on the screen soon.
  - To maximize efficiency you shouldn't be prefetching / should stop prefetching items that are unlikely to appear on the screen.
    
## Tasks

### Architecture
1. **Refactor into a Presentation pattern** to allow unit testing the project. Eg.: MVP, MVVM, MVI, etc.
2. **Implement FeedRepository** to take care of the pagination and enables seamless infinite scrolling experience.

### Logic
1. **Implement seamless one directional scrolling** (from newest to oldest items). After launching the app I should be able to seamlessly scroll down to older items.
2. **Implement unit test** for the presentation layer.

## Extra Points
- Implement Pull-to-Refresh to display newly generated items.
- Unit test the code.

## Google
You can use google during the interview.
