export interface IVenue {
  id?: number;
  name?: string;
  description?: string;
  address?: string;
  phoneNumber?: string;
  location?: string;
}

export class Venue implements IVenue {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public address?: string,
    public phoneNumber?: string,
    public location?: string
  ) {}
}
