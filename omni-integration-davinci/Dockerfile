FROM python:3

WORKDIR /code


COPY requirements.txt requirements.txt
RUN pip3 install --no-cache-dir -r requirements.txt

ENV API_KEY=$API_KEY

EXPOSE 8083
COPY . .

CMD ["python3",  "index.py"]
