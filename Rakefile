task :default => [:clean, "venv:build", "venv:serve"]


desc "Set up git hooks"
task :hooks do
    Dir.chdir('.git/hooks') do
        sh "ln -sf ../../hooks/pre-commit pre-commit"
    end
end

namespace :venv do
    VENV = "env"

    desc "Build the virtual environment"
    task :build => :hooks do
        unless Dir.exists? VENV
            sh "virtualenv -p python2.7 #{VENV}"
        end

        sh "#{VENV}/bin/pip install -U -r requirements.txt"
    end

    desc "Drop into the environment python shell"
    task :shell do
        sh "#{VENV}/bin/python"
    end

    desc "Run the development server"
    task :serve do
        sh "#{VENV}/bin/python server.py"
    end

    desc "Update the html fixtures"
    task :update do
        sh "PYTHONPATH=`pwd` #{VENV}/bin/python scripts/update_fixtures"
    end
end

CONTAINER_NAME = "shelf"

def run_container(daemon=false)
    if daemon
        sh "docker-compose up -d #{CONTAINER_NAME}"
    else
        sh "docker-compose up #{CONTAINER_NAME}"
    end
end

def run_container_command(command)
    sh "docker-compose run --rm #{CONTAINER_NAME} #{command}"
end

namespace :container do
    desc "Build the virtual environment"
    task :build => :hooks do
        sh "docker-compose build #{CONTAINER_NAME}"
    end

    desc "Drop into the environment python shell"
    task :shell do
        run_container_command "python"
    end

    desc "Run the development server"
    task :serve do
        run_container
    end

    desc "Update the html fixtures"
    task :update do
        run_container_command "python scripts/update_fixtures"
    end
end

desc "Scrapes the site for items"
task :scrape do
    run_container(daemon=true)
    sh "sleep 1"
    sh "scripts/scrape $(boot2docker ip):9000"
end

desc "Clean the working directory"
task :clean do
    sh "rm -rf **/*.pyc"
    sh "rm -rf **/__pycache__"
    sh "rm -f *.log*"
end
