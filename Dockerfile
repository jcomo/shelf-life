FROM python:2.7

COPY requirements.txt /tmp/requirements.txt
RUN pip install --no-cache-dir -r /tmp/requirements.txt

ENV PYTHONPATH /app

COPY . /app
WORKDIR /app

EXPOSE 9000

CMD ["python", "server.py", "--port", "9000"]
