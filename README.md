# Bico-Reuse

## Overview

### Problem Statement
The problem our app is trying to solve is reducing the amount of unwanted items on
campuses and leftover items at the end of the school year. We have seen at the end of the year that many items are left in student dorms and thrown away due to the hassle of packing and relocating. We hope to minimize the amount of items wasted by allowing students to advertise their unwanted items throughout the year. The system gives students the opportunity to donate/sell their belongings to others in the same institution without having to involve a third party, like the school, slowing down the process. This way, students can quickly get rid of unwanted items or quickly acquire items they would perhaps need throughout the semester instead of at the end or beginning of the semester. In other words, our system strives to circulate items among the student body as a way to minimize waste.


## Product Spec

### Description

Our system consists of 3 main functionalities:
* Creating user profiles: The users would be able to create profiles on the app, providing their personal information, including their name, profile picture, contact information, institution, and history of items sold/listed. Each user has their own personal profile page that they can add, delete, and edit their information.
* Posting items: The users could post pictures and descriptions of items they no longer need. The post includes the title, item description, price, photo(s), location, condition, availability, contact information, and date posted. Regarding a post, sellers and buyers have different interfaces:
    * Sellers can edit the post, see how many people bookmarked/watched the post, delete the post once the item is sold, etc.
    * Buyers can watch and bookmark the post.
* Browsing and searching items: The users would be able to browse and search for available items in the app based on price, item keywords, location, and date, which could be implemented using a filter system. In our app, the home page will start out as a feed of seller posts, and the user could start browsing and searching from there.

### User Stories (Mobile and Web)

**Android App User Stories**

- [x] As a user, I can register with my email and log in. At registration, I will be asked to create a new user profile with their name, phone number, etc.
- [x] As a seller, I can create a new post for sale, in which I can add information about the item such as the price, pictures, and description
- [x] As a buyer, I can view a list of items for sale from other sellers on the homepage
- [x] As a buyer, I can further view another seller’s post from the list of items for sale on the homepage
- [x] As a seller, I can edit my currently active posts such as updating the description and price
- [x] As a user, I can view another user’s profile, including their name, profile picture, contact information, institution, and history of items sold/listed.
- [x] As a user, I can edit my profile information such as my email, name, and phone
number on my profile page
- [x] As a buyer, I can bookmark posts that I am interested in and view my
bookmarked posts on my profile page.

**Web App User Stories**

- [x] As a user, I can register and log in. At registration, I will be asked to create a new user profile with their name, phone number, etc.
- [x] As a seller, I can create a new post for sale, in which I can add information about the item such as the price, pictures, and description
- [x] As a seller, I can delete my posts that I have created
- [x] As a buyer, I can view a list of items for sale from other sellers on the homepage
- [x] As a buyer, I can further view another seller’s post from the list of items for sale on the homepage
- [x] As a seller, I can edit my currently active posts such as updating the description and price
- [x] As a user, I can view another user’s profile, including their name, profile picture, contact information, institution, and history of items sold/listed.
- [x] As a user, I can edit my profile information such as my email, name, and phone
number on my profile page.
- [x] As a buyer, I can bookmark posts that I am interested in and view my
bookmarked posts on my profile page.



## Data

### Models

#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | post ID      | Integer   | unique id for post |
   | seller ID        | Integer| unique id for user's who is selling |
   | description         | String     | item description |
   | price         | double     | item price |
   | images url         | String     | item's image url string|
   | status         | Enum     | status of item (e.g. available, sold, etc. |
   | title         | String     | item title |
   | date         | String     | item post date |

#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | user ID      | Integer   | unique id for user |
   | email       | String| user email |
   | name       | String | user name |
   | number    | String     | user phone number|
   | history of items    | Array     | list of user's items for sale |
   | image url  | String    | url string of user's image |
   | college   | String     | user's school name  |
   | bookmarks   | Array     | list of user's saved posts |


## How to run

1. Run `npm install` to install all the dependencies needed to run the app. 
2. Set an environment variable called DB_PASS with the database password to ensure that you can connect to the database. 
3. Run the command `npm run make` to concurrently run the express app and the react app.
