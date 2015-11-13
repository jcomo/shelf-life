task :default => [:clean, :build, :serve]

CONTAINER_NAME = "shelf"

def run_container(daemon=false)
    if daemon
        sh "docker-compose up -d #{CONTAINER_NAME}"
    else
        sh "docker-compose up #{CONTAINER_NAME}"
    end
end

# Run a command within the container
def cc(command)
    sh "docker-compose run --rm #{CONTAINER_NAME} #{command}"
end

desc "Build the application container"
task :build => :hooks do
    sh "docker-compose build #{CONTAINER_NAME}"
end

desc "Drop into the container python shell"
task :shell do
    cc "python"
end

namespace :test do
    desc "Run unit tests"
    task :unit do
        cc "env py.test tests/unit"
    end

    desc "Run functional tests"
    task :functional do
        cc "env py.test tests/functional"
    end

    desc "Run integration tests"
    task :integration do
        cc "env py.test tests/integration"
    end

    desc "Run sanity tests"
    task :sanity do
        cc "env py.test tests/sanity"
    end

    desc "Run all tests"
    task :all do
        cc "env py.test"
    end
end

desc "Run basic tests"
task :test => ["test:unit", "test:functional", "test:sanity"]

desc "Run the development server"
task :serve do
    run_container
end

desc "Update the html fixtures"
task :update do
    cc "python scripts/update_fixtures"
end

desc "Set up git hooks"
task :hooks do
    Dir.chdir('.git/hooks') do
        sh "ln -sf ../../hooks/pre-commit pre-commit"
    end
end

desc "Scrapes the site for items"
task :scrape do
    run_container(daemon=true)
    sh "sleep 1"
    sh "scripts/scrape $(docker-machine ip dev):9000"
end

def rmall(pattern)
    sh "find . -name '#{pattern}' | xargs rm -rf"
end

desc "Clean the working directory"
task :clean do
    rmall "*.pyc"
    rmall "__pycache__"
    sh "rm -f *.log*"
end
