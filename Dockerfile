FROM python:2.7

COPY requirements-common.txt /tmp/requirements.txt
RUN pip install --no-cache-dir -r /tmp/requirements.txt

COPY requirements-test.txt /tmp/requirements-test.txt
RUN pip install --no-cache-dir -r /tmp/requirements-test.txt

RUN pip install pep8

ENV PYTHONPATH /app

COPY . /app
WORKDIR /app

EXPOSE 9000

CMD ["python", "server.py", "--port", "9000"]
