# Sheasy - Share Easy
[![jCenter](https://img.shields.io/badge/License-GPLv3-yellow.svg
)](https://github.com/Foso/Sheasy/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![All Contributors](https://img.shields.io/badge/all_contributors-1-range.svg?style=flat-square)](#contributors)
  <a href="https://twitter.com/intent/tweet?text=Hey, check out Sheasy - Share Easy Android App https://github.com/Foso/Sheasy via @jklingenberg_ #Android 
"><img src="https://img.shields.io/twitter/url/https/github.com/angular-medellin/meetup.svg?style=social" alt="Tweet"></a>

This an Android App like Airdroid to manage files on your Android Device through a WebInterface in the Browser

## 🚩 Table of Contents

- [Introduction](#introduction)
- [Features](#-features)
- [Roadmap](#-roadmap)
- [Architecture](#-architecture)
- [Docs](#-docs)
- [Feedback](#feedback)
- [Contributors](#-contributors)
- [Acknowledgments](#acknowledgments)

## Introduction



A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## 🎨 Features
A few of the things you can do with Sheasy:
- download apks
- download files
- receive notifications in browser
- get device informations
- get contacts


## 🗺️ Roadmap
- [ ] show screen recording in browser

# 🏠 Architecture

## 🛠️ Built With
### Android
[![jCenter](https://img.shields.io/badge/minSDK-21-yellow.svg
)](https://github.com/Foso/Sheasy/blob/master/LICENSE)
[![jCenter](https://img.shields.io/badge/compileSdk-27-yellow.svg
)](https://github.com/Foso/Sheasy/blob/master/LICENSE)
[![jCenter](https://img.shields.io/badge/targetSdk-27-yellow.svg
)](https://github.com/Foso/Sheasy/blob/master/LICENSE)

### Backend
* [Ktor](https://github.com/ktorio/ktor) - Used as Backend
* [NanoHttpd](https://github.com/NanoHttpd/nanohttpd) - Only used for WebSockets, because Ktor's websocket need API23

### Frontend
* [React.js](https://reactjs.org/) - Used for Web Frontend



## API
I integrated a swagger api endpoint. You can find it at ip:8766/swagger.
I wanted to to use something like swagger annotations, but i couldn't figure it out, how to use it with ktor.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## 📙 Docs

What things you need to install the software and how to install them

## 💬 Contributors 

This project follows the [all-contributors](https://github.com/kentcdodds/all-contributors) specification and is brought to you by these [contributors](./CONTRIBUTORS.md).

## ✍️ Feedback

Feel free to send us feedback on [Twitter](https://twitter.com/gitpointapp) or [file an issue](https://github.com/gitpoint/git-point/issues/new). Feature requests are always welcome. If you wish to contribute, please take a quick look at the [guidelines](./CONTRIBUTING.md)!

If there's anything you'd like to chat about, please feel free to join our [Gitter chat](https://gitter.im/git-point)!

## 📜 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/Foso/Sheasy/blob/master/LICENSE) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

