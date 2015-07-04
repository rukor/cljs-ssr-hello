# cljs-ssr-hello

Simple isomorphic CLJS app running on NodeJS. This simple app shows how to use cljs-ssr-node with either [OM](https://github.com/omcljs/om) or [Reagent](https://github.com/reagent-project/reagent).

To use OM, simply change this [line](https://github.com/rukor/cljs-ssr-hello/blob/master/src/server/hello/server/core.cljs#L24) from: 

```clojure
                  :renderer reagent-renderer
```

to:

```clojure
                  :renderer om-renderer
```

and then change this [line](https://github.com/rukor/cljs-ssr-hello/blob/master/src/app/hello/app/main.cljs#L40) from:

```clojure
    (render-reagent-home app-state service router)))
```

to 

```clojure
    (render-om-home app-state service router)))
```


## Running and deploying

To run:

```
git clone https://github.com/rukor/cljs-ssr-hello.git
cd cljs-ssr-hello
lein build
npm install
node resources/index.js
```

To deploy to [heroku](http://www.heroku.com) (or similar PaaS), first generate the package.json as follows:

```
lein with-profile prod build
npm install 
```

Then follow [heroku's instructions](https://devcenter.heroku.com/articles/getting-started-with-nodejs#deploy-the-app) for a typical nodejs app

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

# License

EPL
