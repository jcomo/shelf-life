VENV = "env"

task :default => [:build, :serve]

desc "Build the virtual environment"
task :build do
    unless Dir.exists? VENV
        sh "virtualenv -p python2.7 #{VENV}"
    end

    sh "#{VENV}/bin/pip install -U -r requirements.txt"
end

desc "Run the development server"
task :serve do
    sh "#{VENV}/bin/python server.py"
end

desc "Update the html fixtures"
task :update do
    sh "PYTHONPATH=`pwd` #{VENV}/bin/python scripts/update_fixtures.py"
end

desc "Clean the working directory"
task :clean do
    sh "rm -rf **/*.pyc"
    sh "rm -rf **/__pycache__"
end
