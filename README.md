# Sheasy - Share Easy [![Sheasy](https://img.shields.io/badge/License-GPLv3-yellow.svg)](https://github.com/Foso/Sheasy/blob/master/LICENSE)
This an Android App like Airdroid to manage files on your Android Device through a WebInterface in the Browser

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Feedback](#feedback)
- [Contributors](#contributors)
- [Build Process](#build-process)
- [Backers](#backers-)
- [Sponsors](#sponsors-)
- [Acknowledgments](#acknowledgments)

## Introduction

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![jCenter](https://img.shields.io/badge/License-GPLv3-yellow.svg
)](https://github.com/Foso/Sheasy/blob/master/LICENSE)
[![All Contributors](https://img.shields.io/badge/all_contributors-1-range.svg?style=flat-square)](#contributors)

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

## Features
A few of the things you can do with Sheasy:
- download apks
- download files
- receive notifications in browser
- get device informations
- get contacts


## Roadmap
- see screen recording in browser

# Architecture

## API
I integrated a swagger api endpoint. You can find it at ip:8766/swagger.
I wanted to to use something like swagger annotations, but i couldn't figure it out, how to use it with ktor.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With
* [Ktor](https://github.com/ktorio/ktor) - Used as Backend
* [NanoHttpd](https://github.com/NanoHttpd/nanohttpd) - Only used for WebSockets, because Ktor's websocket need API23
* [React.js](https://reactjs.org/) - Used for Web Frontend


## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Contributors

This project follows the [all-contributors](https://github.com/kentcdodds/all-contributors) specification and is brought to you by these [awesome contributors](./CONTRIBUTORS.md).

## Feedback

Feel free to send us feedback on [Twitter](https://twitter.com/gitpointapp) or [file an issue](https://github.com/gitpoint/git-point/issues/new). Feature requests are always welcome. If you wish to contribute, please take a quick look at the [guidelines](./CONTRIBUTING.md)!

If there's anything you'd like to chat about, please feel free to join our [Gitter chat](https://gitter.im/git-point)!

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/Foso/Sheasy/blob/master/LICENSE) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

