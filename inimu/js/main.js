/* inimu — main.js */

(function () {
  'use strict';

  const hamburger = document.getElementById('hamburger');
  const mobileNav = document.getElementById('mobileNav');

  /* ---- Hamburger menu ---- */
  if (hamburger && mobileNav) {
    hamburger.addEventListener('click', () => {
      const isOpen = hamburger.classList.toggle('is-open');
      hamburger.setAttribute('aria-expanded', String(isOpen));
      mobileNav.classList.toggle('is-open', isOpen);
      mobileNav.setAttribute('aria-hidden', String(!isOpen));
      document.body.style.overflow = isOpen ? 'hidden' : '';
    });

    mobileNav.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', closeNav);
    });

    function closeNav() {
      hamburger.classList.remove('is-open');
      hamburger.setAttribute('aria-expanded', 'false');
      mobileNav.classList.remove('is-open');
      mobileNav.setAttribute('aria-hidden', 'true');
      document.body.style.overflow = '';
    }
  }

  /* ---- Instagram tabs ---- */
  const igTabs = document.querySelectorAll('.ig-tab');
  igTabs.forEach(tab => {
    tab.addEventListener('click', () => {
      igTabs.forEach(t => t.classList.remove('is-active'));
      tab.classList.add('is-active');
    });
  });

})();
