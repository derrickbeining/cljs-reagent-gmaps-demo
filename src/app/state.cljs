(ns app.state
  (:require [reagent.core :as r]))

(def state (r/atom {:gmaps-key nil
                    :steps []
                    :originCoords {:lat 0 :lng 0}
                    :destinationCoords {:lat 0 :lng 0}
                    :polylinePath []
                    :bounds {:north nil
                             :east nil
                             :south nil
                             :west nil}}))