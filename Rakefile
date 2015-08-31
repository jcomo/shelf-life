VENV = "env"

task :default => [:build, :serve]

task :build do
    unless Dir.exists? VENV
        sh "virtualenv -p python2.7 #{VENV}"
    end

    sh "#{VENV}/bin/pip install -U -r requirements.txt"
end

task :serve do
    sh "#{VENV}/bin/python server.py"
end

task :clean do
    sh "rm -rf **/*.pyc"
    sh "rm -rf **/__pycache__"
end
