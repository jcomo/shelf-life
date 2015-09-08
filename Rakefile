PRE_COMMIT_HOOK = ".git/hooks/pre-commit"

task :default => [:clean, "venv:build", "venv:serve"]

file PRE_COMMIT_HOOK do |t|
    File.open(t.name, 'w', 0755) do |f|
        f.puts "#!/bin/sh"
        f.puts "echo Running PEP8 checks..."
        f.puts "pep8 --ignore E501 scripts shelf"
    end
end

desc "Set up git hooks"
task :hooks => [PRE_COMMIT_HOOK]

namespace :venv do
    VENV = "env"

    desc "Build the virtual environment"
    task :build => :hooks do
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
end

namespace :container do
    NAME = "shelf"

    desc "Build the virtual environment"
    task :build => :hooks do
        sh "docker-compose build #{NAME}"
    end

    desc "Run the development server"
    task :serve do
        sh "docker-compose up #{NAME}"
    end

    desc "Update the html fixtures"
    task :update do
        sh "docker-compose run --rm #{NAME} python scripts/update_fixtures.py"
    end
end

desc "Clean the working directory"
task :clean do
    sh "rm -rf **/*.pyc"
    sh "rm -rf **/__pycache__"
    sh "rm -f *.log*"
    sh "rm -f #{PRE_COMMIT_HOOK}"
end
