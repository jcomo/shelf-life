task :default => [:clean, :test]

desc "Run tests"
task :test do
  sh "mvn test"
end

desc "Build the service"
task :build do
  sh "mvn package"
  sh "docker build -t foodie ."
end

desc "Run the service"
task :serve => :build do
  sh "docker-compose up"
end

desc "Clean the working directory"
task :clean do
  sh "rm -rf target/"
end
