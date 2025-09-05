CREATE INDEX IF NOT EXISTS fnr_index ON public.vurdering USING btree ((((json -> 'datagrunnlag'::text) ->> 'fnr'::text)));
