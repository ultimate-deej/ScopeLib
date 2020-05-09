# Introduction

I've made this library for myself, and it is still in the process of shaping up.
However, it's already usable. If you have any questions on how to or why use it, feel free to open an issue.

# Overview

In short, this library helps you link your Toothpick scope tree with *logical* screens of your fragment-based single-activity application.

### Key features and limitations:

- It's designed to utilize the Android ability to fully restore apps, including all Fragments, even after the process has been killed.
- At the same time, it ties Toothpick scopes lifetime to logical lifetime of screens as opposed to tying to Fragment/Activity recreations caused by lifecycle needs.
- The above means that when managed by this library, Toothpick scopes outlive individual Fragment/Activity instances. Your Toothpick scope won't be reopened on orientation change and similar.
- An implication of that is that you don't generally register shorter-lived objects (e.g. View, Fragment, or Context) within a scope, although it is possible by using a holder object.
- It's designed to add as little boilerplate as possible. In fact, based on my own use, the amount of boilerplate actually decreases compared to manual scope management.
- If used properly, this library will recreate your whole Toothpick scope tree even after process death, which in turn will help your restore the whole app state. For simpler cases like orientation change, when it's unreasonable to reinitialize your data every time configuration change happens, it helps you keep such data in memory.
