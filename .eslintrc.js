module.exports = {
  extends: [
    'react-app',
    'react-app/jest',
    'plugin:jsx-a11y/recommended',
  ],
  plugins: ['jsx-a11y'],
  rules: {
    // You can customize accessibility rules here.
    // For example, to downgrade a rule from an error to a warning:
    // 'jsx-a11y/anchor-is-valid': 'warn',
  },
};
