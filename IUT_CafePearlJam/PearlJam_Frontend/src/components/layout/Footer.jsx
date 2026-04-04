import { Link } from 'react-router-dom';
import { Facebook, Twitter, Instagram, Mail, Phone, MapPin } from 'lucide-react';

/**
 * Footer: The footer component for the application.
 */
export function Footer() {
  const currentYear = new Date().getFullYear();

  const footerLinks = [
    {
      title: 'PearlJam',
      links: [
        { name: 'About Us', path: '/about' },
        { name: 'How it works', path: '#how-it-works' },
        { name: 'Restaurants', path: '/restaurants' },
        { name: 'Contact Us', path: '/contact' },
      ],
    },
  ];

  return (
    <footer className="bg-surface/50 pt-20 pb-12">
      <div className="container mx-auto px-6">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-12 mb-16">
          <div className="lg:col-span-2">
            <Link to="/" className="mb-6 inline-block">
              <span className="font-serif text-4xl font-bold text-accent tracking-tighter">
                PEARL<span className="text-text-primary">JAM</span>
              </span>
            </Link>
            <p className="body-lg max-w-sm mb-8 text-text-muted">
              Bringing the freshest editorial-grade culinary experiences directly to your doorstep. We curate. We deliver. You savour.
            </p>
            <div className="flex items-center gap-4">
              <a href="https://github.com/safwan-satil" target="_blank" rel="noopener noreferrer" className="label-md font-bold text-accent hover:underline">
                GitHub
              </a>
              <span className="text-text-muted">•</span>
              <a href="https://linkedin.com" target="_blank" rel="noopener noreferrer" className="label-md font-bold text-accent hover:underline">
                LinkedIn
              </a>
            </div>
          </div>

          {footerLinks.map((section) => (
            <div key={section.title}>
              <h4 className="label-md font-bold text-text-primary uppercase mb-6 tracking-widest leading-6 border-b border-border/50 pb-2">
                {section.title}
              </h4>
              <ul className="space-y-4">
                {section.links.map((link) => (
                  <li key={link.name}>
                    <Link to={link.path} className="label-md font-medium text-text-muted hover:text-accent transition-colors">
                      {link.name}
                    </Link>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>

        <div className="flex flex-col md:flex-row items-center justify-between pt-12 border-t border-border/30 gap-6">
          <div className="flex flex-col md:flex-row items-center gap-8">
            <div className="flex items-center gap-2 text-text-muted">
              <MapPin size={16} />
              <span className="label-md">PearlJam St. Culinary District</span>
            </div>
            <div className="flex items-center gap-2 text-text-muted">
              <Phone size={16} />
              <span className="label-md">+1 (888) PEARL-JAM</span>
            </div>
            <div className="flex items-center gap-2 text-text-muted">
              <Mail size={16} />
              <span className="label-md">hello@pearljam.com</span>
            </div>
          </div>
          
          <p className="label-md text-text-muted">
            &copy; {currentYear} PearlJam. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  );
}
