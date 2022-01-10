const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth20').Strategy;

passport.serializeUser(function(user, done) {
   
    done(null, user);
  });
  
passport.deserializeUser(function(user, done) {
  
    done(null, user);
});

passport.use(new GoogleStrategy({
    clientID: "583423481012-4nug3f6hq8f5mkp3bbb8kaiqbes6bn14.apps.googleusercontent.com",
    clientSecret: "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",
    callbackURL: "http://localhost:3000/google/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    
    return done(null, profile);
  }
  ));