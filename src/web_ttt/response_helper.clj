(ns web-ttt.response-helper)

(defn set-response-to
  [status response]
  (.setHTTPVersion response "HTTP/1.1")
  (.setStatus response status))