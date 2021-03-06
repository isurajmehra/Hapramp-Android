# HapRamp Android App [![Build Status](https://travis-ci.org/hapramp/Hapramp-Android.svg?branch=master)](https://travis-ci.org/hapramp/Hapramp-Android)
---
HapRamp is a Steem Blockchain based social media for the creative communities. This is the Android application for users to use the platform.

### Android build dependency

 Minimum SDK Version: 19
 
 Target SDK Version: 27
 
 Build Tool Version: 27.0.3
 
 ### How to build the app
 
 **Debug Version**
 There are no special dependencies for building debug version of the app.
  - Clone the repo 
  - Perform a gradle sync
  - Run the app
 
 **Release Version**
 Building a release version of apk requires you a `keystore` file.
 For privacy reasons, we kept this file secret.
 
 You can generate a new `keystore` file using:
 `Build` -> `Generate Signed Apk`
 
 ### Login steps:
  - Create a [Steem Account](https://steemit.com) (Leave it if already have one)
  - Login into your steemit account.
  - Go to Profile, a popup will appear like:
  <img width="203" alt="screen shot 2018-06-06 at 3 01 44 pm" src="https://user-images.githubusercontent.com/10809719/41029892-9550815a-699a-11e8-89d3-3d9c2fa6784a.png">
  
   - Choose `Wallet`
   - Click on `Permissions` Tab <img width="393" alt="screen shot 2018-06-06 at 3 03 35 pm" src="https://user-images.githubusercontent.com/10809719/41029981-d927c960-699a-11e8-868a-1a7cc90a2e16.png">
   
   You will get this bar
   <img width="1119" alt="screen shot 2018-06-06 at 3 05 24 pm" src="https://user-images.githubusercontent.com/10809719/41030088-2246207e-699b-11e8-9276-cc644014070b.png">
   
   - You need to click on `SHOW PRIVATE KEY`
   
   - Now click on the QR Code <img width="36" alt="screen shot 2018-06-06 at 3 07 53 pm" src="https://user-images.githubusercontent.com/10809719/41030210-6b813594-699b-11e8-8ac0-5b426db900bb.png">.
   
   A enlarged QR code appears.
   (Don`t share this Private Posting Key with anyone)
   - Now Open `Hapramp` app,
   - Click on Scan button and scan to login.
   

### Security concerns for contributors
Any Private keys among 
**Active Key**,
**Owner Key**,
**Posting Key**

related to your account should not be pushed/commited along with codes. We are not liable for your accout`s security breach if done via code of this repository.

We store `Private Posting key` on users device to provide seamless experience while postig a blog or commenting etc.
Our servers donot store this key neither you should try to upload this key anywhere.


### Features
 - **Login:** Users can Login using the Private Posting Key of their respective Steem (https://steemit.com) accounts.
 - **Communities:** Select the communities of your interest from the 9 pre-defined communities: Art, Dance, Design, Dramatics, Fashion, Literature, Music, Photography, and Travel.
 - **Feed:**  User feed will be generated based on their preference of the communities.
 - **Content Filter:** Users can switch between the specific community to receive filtered content.
 - **Content posting:** There are two ways to share content on HapRamp: Micro-blog multimedia post and Blog post.
 - **Multi-media:** The application supports Image, Audio file, and YouTube video embedding.
 - **Mirco-blogs:**  The Micro-blog multimedia posts can contain one media file with text and tags.
 - **Blog posts:** The Blog posts can contain multiple media files and has the support for formatting.
 - **Community Tag:** The users need to select the relevant communit(y/ies) (max 3) before posting the content.
 - **Comments:**  The application support comments on posts.
 - **Content Voting:** The Users can rate posts on a scale of 1 to 5 stars which signifies variable Steem voting power.
 - **Rewards:** The users earn Steem Dollars according to how much ratings they received from how many people.
 - **Steem Power:** The earning depends on the Steem Power of respective users that they accumulate over time by creating and curating content on the platform.
 - **User Profile:** On the user profile section, the users edit profile picture, bio, and interests.
 - **User Search:** Search existing Steem users from the search bar.
 - **Notification:** users will receive notifications for the ratings and comments on their own content.


 


#### Screenshots:
<img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/login.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/scanner.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/home.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/post_creation.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/article_creation.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/comments.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/rating.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/user_profile.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/trending.png" width="160px" height="290px"><img src="https://github.com/hapramp/Hapramp-Android/blob/master/screenshots/youtube_search_and_embed.png" width="160px" height="290px">



MIT License

Copyright (c) 2018 HapRamp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

