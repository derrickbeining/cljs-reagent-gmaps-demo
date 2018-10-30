# ClojureScript Reagent Google Maps Demo

Spent a weekend learning [Clojure](https://clojure.org/)/[ClojureScript](https://clojurescript.org/index) and whipped up this little app to apply what I learned to something concrete.

## Installation
Prequisites: 

* `node.js` v6.0.0+
* `npm` (included with Node.js v6.0.0+)
* Java SDK (Version 8+)
  
Clone this repo, then run
```
$ cd cljs-reagent-gmaps-demo
$ npm install
```

## To Run the App
**Prerequisites:**

* you will need to add the file `.env/production` containing the following environment variable:

```env
GMAPS_API_KEY=<ENTER_YOUR_KEY_HERE>
```
With environment variable(s) in place, you can start the app by running :
```
$ npm start
```

This will compile the source and start a `node.js` server to serve the app which will be accessible at `http://localhost:3000`. Don't be surprised if it takes a while to compile.