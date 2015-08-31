VENV = "env"
PRE_COMMIT_HOOK = ".git/hooks/pre-commit"

task :default => [:clean, :build, :serve]

file PRE_COMMIT_HOOK do
    File.open(PRE_COMMIT_HOOK, 'w', 0755) do |f|
        f.puts "#!/bin/sh"
        f.puts "echo Running PEP8 checks..."
        f.puts "#{VENV}/bin/pep8 --ignore E501 scripts shelf"
    end
end

desc "Set up git hooks"
task :hooks => [PRE_COMMIT_HOOK]

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

desc "Clean the working directory"
task :clean do
    sh "rm -rf **/*.pyc"
    sh "rm -rf **/__pycache__"
    sh "rm -f #{PRE_COMMIT_HOOK}"
end
