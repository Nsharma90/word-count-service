# word-counter-service
Returns the word count and treat it same word if word is another language


# default url
localhost:8080/counter

# request parameters
text= something that contains only aphabets including space
find= one word otherwise it'll not find anything

# sample curl request to rest endpoint

curl -X POST \
  http://localhost:8080/counter \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -H 'postman-token: c9706ec0-13da-3c47-a8c3-1136b82fbaa6' \
  -F 'text=German word for flower is Blume' \
  -F 'find=flor'
