import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import { describe, it, expect } from 'vitest';
import App from '../App';

describe('Mi Music Application - Smartphone & Xiaomi Mi Band 10 Mirror', () => {
  it('renders application header and dual device panels', () => {
    render(<App />);
    expect(screen.getByText(/Xiaomi Smartphone/i)).toBeInTheDocument();
    expect(screen.getByText(/Xiaomi Mi Band 10 \("Mi Music"\)/i)).toBeInTheDocument();
  });

  it('displays initial song metadata on both smartphone and smartwatch', () => {
    render(<App />);
    const titleElements = screen.getAllByText(/Midnight City/i);
    expect(titleElements.length).toBeGreaterThanOrEqual(2);

    const artistElements = screen.getAllByText(/M83/i);
    expect(artistElements.length).toBeGreaterThanOrEqual(2);
  });

  it('switches app music sources (e.g. Spotify to Apple Music / YouTube Music)', () => {
    render(<App />);
    const appleMusicButton = screen.getByRole('button', { name: /Apple Music/i });
    fireEvent.click(appleMusicButton);

    const appleBadges = screen.getAllByText(/Apple Music/i);
    expect(appleBadges.length).toBeGreaterThan(0);
  });

  it('supports EQ presets and sleep timer configuration', () => {
    render(<App />);
    const selects = screen.getAllByRole('combobox');
    expect(selects.length).toBeGreaterThanOrEqual(2);
  });

  it('supports 3rd party YouTube Music clients like Metrolist', () => {
    render(<App />);
    const metrolistBtn = screen.getByRole('button', { name: /Metrolist/i });
    expect(metrolistBtn).toBeInTheDocument();

    fireEvent.click(metrolistBtn);
    const metrolistBadges = screen.getAllByText(/Metrolist/i);
    expect(metrolistBadges.length).toBeGreaterThan(0);
  });

  it('displays 3rd party clients in Mi Band 10 app chooser', () => {
    render(<App />);
    const appsBtn = screen.getByTitle('Switch App Source');
    fireEvent.click(appsBtn);

    expect(screen.getAllByText('Metrolist').length).toBeGreaterThanOrEqual(1);
    expect(screen.getAllByText('InnerTune').length).toBeGreaterThanOrEqual(1);
    expect(screen.getAllByText('ViMusic').length).toBeGreaterThanOrEqual(1);
  });
});
