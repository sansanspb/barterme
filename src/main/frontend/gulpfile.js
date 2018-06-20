let gulp       = require('gulp'),
    sass         = require('gulp-sass'),
    browserSync  = require('browser-sync'),
    autoprefixer = require('gulp-autoprefixer'),
    cleanCSS = require('gulp-clean-css'),
    babili = require('gulp-babili'),
    gutil = require('gulp-util'),
    browserify = require('gulp-browserify'),
    pug = require('gulp-pug'),
    htmlmin = require('gulp-htmlmin');

gulp.task('sassJava', function(){ // Создаем таск Sass
    gulp.src('./scss/fonts/*')
        .pipe(gulp.dest('../webapp/css/fonts/'));

    return gulp.src('./scss/main.scss') // Берем источник
        .pipe(sass()) // Преобразуем Sass в CSS посредством gulp-sass
        .pipe(cleanCSS()) // Сжимаем конечный css
        .pipe(autoprefixer(['last 15 versions', '> 1%', 'ie 8', 'ie 7'], { cascade: true })) // Создаем префиксы
        .pipe(gulp.dest('../webapp/css')) // Выгружаем результата в папку app/css
        .pipe(browserSync.reload({stream: true})) // Обновляем CSS на странице при изменении
});

gulp.task('sass', function(){ // Создаем таск Sass
    gulp.src('./scss/fonts/*')
        .pipe(gulp.dest('app/css/fonts/'));

    return gulp.src('./scss/main.scss') // Берем источник
        .pipe(sass()) // Преобразуем Sass в CSS посредством gulp-sass
        .pipe(cleanCSS()) // Сжимаем конечный css
        .pipe(autoprefixer(['last 15 versions', '> 1%', 'ie 8', 'ie 7'], { cascade: true })) // Создаем префиксы
        .pipe(gulp.dest('app/css')) // Выгружаем результата в папку app/css
        .pipe(browserSync.reload({stream: true})) // Обновляем CSS на странице при изменении
});

gulp.task('pugJava', () => {
    return gulp.src([
        '!pug/components',
        '!pug/template.pug',
        'pug/*.pug'
    ])
        .pipe(pug({
            pretty: true
        }))
        .pipe(htmlmin({
            collapseWhitespace: false,
            includeAutoGeneratedTags: false,
        }))
        .pipe(gulp.dest('../webapp/WEB-INF/templates/')) // Выгружаем результата в папку app/
        .pipe(browserSync.reload({stream: true})) // Обновляем HTML на странице при изменени
});

gulp.task('pug', () => {
    return gulp.src([
        '!pug/components',
        '!pug/template.pug',
        'pug/*.pug'
    ])
        .pipe(pug({
            pretty: true
        }))
        .pipe(htmlmin({
            collapseWhitespace: false,
            includeAutoGeneratedTags: false,
        }))
        .pipe(gulp.dest('app/')) // Выгружаем результата в папку app/
        .pipe(browserSync.reload({stream: true})) // Обновляем HTML на странице при изменени
});

gulp.task('browser-sync', function() { // Создаем таск browser-sync
    browserSync({ // Выполняем browserSync
        server: { // Определяем параметры сервера
            baseDir: 'app' // Директория для сервера - app
        },
        notify: false // Отключаем уведомления
    });
});

gulp.task('jsJava', function() {
    return gulp.src(['js/main.js'])
        .pipe(browserify())
        /*.pipe(babili({
            mangle: {
                keepClassName: true
            }
        }))*/
        .on('error', (error) => {
        gutil.log(gutil.colors.red('[Error]'), error.toString());
        })
        .pipe(gulp.dest('../webapp/js'));
});

gulp.task('js', function() {
    return gulp.src(['js/main.js'])
        .pipe(browserify())
        /*.pipe(babili({
            mangle: {
                keepClassName: true
            }
        }))*/
        .on('error', (error) => {
        gutil.log(gutil.colors.red('[Error]'), error.toString());
})
.pipe(gulp.dest('app/js'));
});


gulp.task('default', ['sassJava', 'pugJava', 'jsJava'], function(){
    gulp.watch('scss/**/*.scss', ['sassJava']); // Наблюдение за sass файлами в папке sass
    gulp.watch('pug/**/*.pug', ['pugJava']); // Наблюдение за sass файлами в папке pug
    gulp.watch('js/**/*.js', ['jsJava']);   // Наблюдение за JS файлами в папке js
});

gulp.task('watch', ['browser-sync', 'sass', 'pug'], function() {
    gulp.watch('scss/**/*.scss', ['sass']); // Наблюдение за sass файлами в папке sass
    gulp.watch('pug/**/*.pug', ['pug']); // Наблюдение за sass файлами в папке pug
    gulp.watch('js/**/*.js', ['js']);   // Наблюдение за JS файлами в папке js
    gulp.watch('app/css/*.css', browserSync.reload); // Наблюдение за HTML файлами в корне проекта
    gulp.watch('app/*.html', browserSync.reload); // Наблюдение за HTML файлами в корне проекта
    gulp.watch('app/js/**/*.js', browserSync.reload);   // Наблюдение за JS файлами в папке js
});
